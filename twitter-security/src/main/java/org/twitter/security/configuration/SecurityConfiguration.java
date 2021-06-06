package org.twitter.security.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.twitter.security.authentication.RestAuthenticationEntryPoint;
import org.twitter.security.filters.CustomCorsFilter;
import org.twitter.security.filters.JwtTokenAuthenticationProcessingFilter;
import org.twitter.security.filters.LoginProcessingFilter;
import org.twitter.security.jwt.SkipPathRequestMatcher;
import org.twitter.security.jwt.TokenExtractor;
import org.twitter.security.providers.JwtAuthenticationProvider;
import org.twitter.security.providers.UserAuthenticationProvider;

/**
 * 
 * @author someshkumar
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {
		"org.twitter.security" }, excludeFilters = @Filter(type = FilterType.REGEX, pattern = "org.twitter.security.configuration"))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	private static final String AUTHENTICATION_URL = "/api/v1/users/login";
	private static final String REGISTER_USER_URL = "/api/v1/users/signup";
	private static final String H2_URL = "/h2/**";
	private static final String API_ROOT_URL = "/api/**";

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private TokenExtractor tokenExtractor;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper objectMapper;

	protected LoginProcessingFilter buildLoginProcessingFilter(String loginEntryPoint) throws Exception {
		LoginProcessingFilter filter = new LoginProcessingFilter(loginEntryPoint, successHandler, failureHandler,
				objectMapper);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(
			List<String> pathsToSkip, String pattern) throws Exception {
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
		JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(failureHandler,
				tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(userAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> permitAllEndpointList = Arrays.asList(AUTHENTICATION_URL, REGISTER_USER_URL, H2_URL);

		http.csrf().disable() // We don't need CSRF for JWT based authentication
				.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests()
				.antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()])).permitAll().and()
				.authorizeRequests().antMatchers(API_ROOT_URL).authenticated() // Protected API End-points
				.and().addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildLoginProcessingFilter(AUTHENTICATION_URL),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL),
						UsernamePasswordAuthenticationFilter.class)
		.headers().frameOptions().sameOrigin();
	}
}
