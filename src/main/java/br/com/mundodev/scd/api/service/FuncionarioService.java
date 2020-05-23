package br.com.mundodev.scd.api.service;

import java.util.Optional;

import br.com.mundodev.scd.api.domain.FuncionarioApi;

public interface FuncionarioService {

	Optional<FuncionarioApi> getFuncionarioByLogin(String login);

}
