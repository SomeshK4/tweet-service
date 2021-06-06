package org.twitter.security.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.twitter.security.dto.ErrorResponseDTO;
import org.twitter.security.enums.ErrorCode;
import org.twitter.security.exceptions.AuthMethodNotSupportedException;
import org.twitter.security.exceptions.InvalidJwtToken;
import org.twitter.security.exceptions.JwtExpiredTokenException;

/**
 * 
 * @author someshkumar
 *
 */
@Component
public class ApiAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;
    
    @Autowired
    public ApiAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }	
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		if (e instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), ErrorResponseDTO.of("Invalid email or password", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), ErrorResponseDTO.of("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof AuthMethodNotSupportedException) {
		    mapper.writeValue(response.getWriter(), ErrorResponseDTO.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		} else if(e instanceof InvalidJwtToken){
			mapper.writeValue(response.getWriter(), ErrorResponseDTO.of("Invalid Jwt token", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		}

		mapper.writeValue(response.getWriter(), ErrorResponseDTO.of("Authentication failed", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
	}
}
