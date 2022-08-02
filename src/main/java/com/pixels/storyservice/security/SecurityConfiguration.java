package com.pixels.storyservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pixels.storyservice.jwt.JwtConfig;
import com.pixels.storyservice.jwt.JwtTokenVerifier;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	JwtConfig jwtConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().headers().frameOptions().disable().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and().authorizeRequests().antMatchers("*")
				.hasAnyAuthority("USER", "User", "*").anyRequest().authenticated().and()
				.addFilterAfter(new JwtTokenVerifier(jwtConfig), UsernamePasswordAuthenticationFilter.class);
	}

}