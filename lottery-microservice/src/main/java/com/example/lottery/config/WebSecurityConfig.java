package com.example.lottery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.lottery.filter.JwtTokenFilter;

@Configuration
public class WebSecurityConfig {
	@Value("${secretKey}")
	private String secret;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService,AuthenticationManager authenticationManager) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		    .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
		                authorizationManagerRequestMatcherRegistry.requestMatchers("/login/**")
		                                                          .permitAll()
		                                                          .anyRequest().authenticated())
		    .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authenticationManager(authenticationManager);
		http.userDetailsService(userDetailsService);
		http.addFilterBefore(new JwtTokenFilter(userDetailsService, secret),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	UserDetailsManager userDetailsManager() {
		UserDetails user = User.withUsername("jack").password("secret").roles("USER","ADMIN").build();
		return new InMemoryUserDetailsManager(user);
	}
}