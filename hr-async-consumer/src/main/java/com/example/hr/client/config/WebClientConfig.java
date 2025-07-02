package com.example.hr.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	private final String hrRestApiUrl;
	
	public WebClientConfig(@Value("${hrRestApiUrl}") String hrRestApiUrl) {
		this.hrRestApiUrl = hrRestApiUrl;
	}
	@Bean
	WebClient createWebClient() {
		return WebClient.builder()
				        .baseUrl(hrRestApiUrl)
				        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				        .build();
	}
}
