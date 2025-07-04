package com.example.lottery.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.lottery.dto.request.WebUser;
import com.example.lottery.service.JwtTokenProvider;

@RestController
@RequestScope
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	
	public LoginController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping
	public String login(@RequestBody WebUser webUser) {
		try {
			var authenticationToken = new UsernamePasswordAuthenticationToken(webUser.username(), webUser.password());
			authenticationManager.authenticate(authenticationToken);
			return jwtTokenProvider.createToken(webUser.username());
		} catch (Exception e) {
			System.out.println("Login has failed!");
			return "Wrong username/password";
		}		
	}
}
