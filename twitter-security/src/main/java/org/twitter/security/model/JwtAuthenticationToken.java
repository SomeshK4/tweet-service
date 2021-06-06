package org.twitter.security.model;


import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import org.twitter.security.jwt.RawAccessJwtToken;

/**
 * An {@link org.springframework.security.core.Authentication} implementation
 * that is designed for simple presentation of JwtToken.
 * 
 * @author someshkumar
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1784186680622657554L;
	private RawAccessJwtToken rawAccessToken;
    private UserContext userContext;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
