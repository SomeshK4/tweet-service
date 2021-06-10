package org.twitter.security.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.twitter.service.problem.ProblemDetail;

/**
 * 
 * @author someshkumar
 *
 */
@Slf4j
@Component
public class ApiAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;
    
    @Autowired
    public ApiAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }	
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException {

		response.setContentType(ProblemDetail.CONTENT_TYPE.toString());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		ProblemDetail problemDetail = new ProblemDetail.Builder()
				.detail(e)
				.status(HttpStatus.UNAUTHORIZED)
				.build();

		mapper.writeValue(response.getWriter(), problemDetail);
		log.error("Authentication error : {}", problemDetail, e);
	}
}
