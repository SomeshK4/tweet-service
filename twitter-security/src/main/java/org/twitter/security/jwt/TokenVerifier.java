package org.twitter.security.jwt;

/**
 * 
 * @author someshkumar
 *
 */
public interface TokenVerifier {
	boolean verify(String jti);
}
