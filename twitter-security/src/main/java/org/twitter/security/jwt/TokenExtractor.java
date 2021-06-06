package org.twitter.security.jwt;


/**
 * 
 * @author someshkumar
 *
 */
public interface TokenExtractor {
    public String extract(String payload);
}
