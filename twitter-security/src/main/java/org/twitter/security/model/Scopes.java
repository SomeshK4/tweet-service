package org.twitter.security.model;


/**
 * 
 * @author someshkumar
 *
 */
public enum Scopes {
    REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
