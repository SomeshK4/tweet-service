package org.twitter.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.twitter.security.model.AccessJwtToken;
import org.twitter.security.model.UserContext;

/**
 * 
 * @author someshkumar
 *
 */
@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     * @param userContext
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getEmail())) 
            throw new IllegalArgumentException("Cannot create JWT Token without email");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(userContext.getEmail());
        claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
          .setExpiration(Date.from(currentTime
              .plusMinutes(settings.getTokenExpirationTime())
              .atZone(ZoneId.systemDefault()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }
}
