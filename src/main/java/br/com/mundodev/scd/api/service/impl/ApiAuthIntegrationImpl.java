package br.com.mundodev.scd.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.mundodev.scd.api.domain.ApiAuthenticationRequest;
import br.com.mundodev.scd.api.domain.ApiAuthenticationResponse;
import br.com.mundodev.scd.api.service.ApiAuthIntegration;

@Service
public class ApiAuthIntegrationImpl implements ApiAuthIntegration {
	
	private final static String API_URL = "https://ereceb.herokuapp.com/api";
	
	private final static String API_AUTHENTICATION_URL = API_URL.concat("/authenticate");
	
	private RestTemplate restTemplate;
	
	@Autowired
	public ApiAuthIntegrationImpl(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public ApiAuthenticationResponse getTokenAuthorization() {
		
		final var apiAuthenticationRequest = new ApiAuthenticationRequest("admin", "admin");
		
		final ResponseEntity<ApiAuthenticationResponse> responseEntity = restTemplate.postForEntity(API_AUTHENTICATION_URL, apiAuthenticationRequest, ApiAuthenticationResponse.class);
		
		return responseEntity.getBody();		
	}

}
