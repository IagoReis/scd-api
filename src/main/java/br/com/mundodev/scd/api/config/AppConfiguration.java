package br.com.mundodev.scd.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

	@Bean(name = "restTemplate")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
