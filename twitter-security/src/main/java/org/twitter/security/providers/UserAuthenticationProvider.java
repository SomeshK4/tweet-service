package org.twitter.security.providers;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.twitter.entity.user.User;
import org.twitter.security.model.Authority;
import org.twitter.security.model.UserContext;
import org.twitter.service.user.IUserService;
import org.twitter.service.user.UserService;

/**
 * 
 * @author someshkumar
 *
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final IUserService userService;

    @Autowired
    public UserAuthenticationProvider(final IUserService userService, @Lazy final BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));
        
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Email or Password not valid.");
        }

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(Authority.ADMIN.toString()));
        UserContext userContext = UserContext.create(user.getEmail(), authorities);
        
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
