package com.nhb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	@Autowired
	private SecurityContextRepository securityRepo;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		logger.debug("set AuthenticationProvider:"+authenticationProvider.getClass());
		auth.authenticationProvider(authenticationProvider);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.securityContext().securityContextRepository(securityRepo).and()
				.authorizeRequests()
				// public content alowwance

				.antMatchers(HttpMethod.GET,"/api/profile/setup").permitAll()
				.antMatchers(HttpMethod.POST,"/japi/langtext/*").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT,"/japi/langtext/*").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH,"/japi/langtext/*").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE,"/japi/langtext/*").hasRole("ADMIN")
				
				.antMatchers(HttpMethod.POST,"/uploadfile/**").hasRole("USER")
				.antMatchers(HttpMethod.POST,"/login").permitAll()
				.antMatchers(HttpMethod.GET,"/user/*").hasRole("USER")
				.antMatchers(HttpMethod.PUT,"/user/*").hasRole("USER")

				
				.antMatchers(HttpMethod.GET,"/loginTest").permitAll()
				.antMatchers("/activate/**").permitAll()	

				.antMatchers("/").permitAll()	
				.antMatchers("/profile/index").permitAll()	
				.antMatchers("/company/**").permitAll()	
				.antMatchers("/building/*").permitAll()	
				.antMatchers("/level/*").permitAll()	
				
				.antMatchers("/index.html").permitAll()
				.antMatchers("/crossdomain.xml").permitAll()
				.antMatchers("/error-notification").permitAll()
				.antMatchers("/robots.txt").permitAll()				
				.antMatchers("/assets/**").permitAll()
				
				
				.antMatchers(HttpMethod.GET,"/*").hasRole("USER")
				.antMatchers(HttpMethod.POST,"/*").hasRole("USER").anyRequest()
				
				
				
				.authenticated();
	}
}
