package br.com.mundodev.scd.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.service.ApiIntegration;

@Service
public class ApiIntegrationImpl implements ApiIntegration {
	
	private final static String API_URL = "https://ereceb.herokuapp.com/api";
	
	private final static String API_URL_FUNCIONARIO_BY_LOGIN = API_URL.concat("/funcionarios/login/{login}");
	
	private RestTemplate apiRestTemplate;
	
	@Autowired
	public ApiIntegrationImpl(final RestTemplate apiRestTemplate) {
		this.apiRestTemplate = apiRestTemplate;
	}
	
	@Override
	public FuncionarioApi getFuncionarioByLogin(final String login) {
		
		final ResponseEntity<FuncionarioApi> responseEntity = apiRestTemplate.getForEntity(API_URL_FUNCIONARIO_BY_LOGIN, FuncionarioApi.class, login);
		
		return responseEntity.getBody();
	}

}
