package org.twitter.security.jwt;

import org.springframework.stereotype.Component;

/**
 * 
 * @author someshkumar
 *
 */
@Component
public class FilterTokenVerifier implements TokenVerifier {
	
	//TODO Implement token verification
	@Override
	public boolean verify(String jti) {
		return true;
	}
}
