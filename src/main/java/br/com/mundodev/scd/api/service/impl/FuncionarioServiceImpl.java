package br.com.mundodev.scd.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.service.ApiIntegration;
import br.com.mundodev.scd.api.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	private ApiIntegration apiIntegration;
	
	@Autowired
	public FuncionarioServiceImpl(final ApiIntegration apiIntegration) {
		this.apiIntegration = apiIntegration;
	}
	
	@Override
	public FuncionarioApi getFuncionarioByLogin(final String login) {
		return apiIntegration.getFuncionarioByLogin(login);
	}
	
}
