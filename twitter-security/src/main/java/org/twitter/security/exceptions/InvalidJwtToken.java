package org.twitter.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author someshkumar
 *
 */
public class InvalidJwtToken extends AuthenticationException {
    private static final long serialVersionUID = -294671188037098603L;
    
    public InvalidJwtToken(String msg) {
        super(msg);
    }
}
