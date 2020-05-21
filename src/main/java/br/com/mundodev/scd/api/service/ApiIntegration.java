package br.com.mundodev.scd.api.service;

import br.com.mundodev.scd.api.domain.FuncionarioApi;

public interface ApiIntegration {

	FuncionarioApi getFuncionarioByLogin(String login);
	
}
