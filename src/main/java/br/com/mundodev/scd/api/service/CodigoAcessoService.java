package br.com.mundodev.scd.api.service;

import java.util.List;
import java.util.Optional;

import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.model.CodigoAcesso;

public interface CodigoAcessoService {

	CodigoAcesso createCodigoAcesso(FuncionarioApi funcionario);

	Optional<CodigoAcesso> getCodigoAcessoPendenteByFuncionario(FuncionarioApi funcionario);

	List<CodigoAcesso> getCodigoAcessoByFuncionarioAndStatus(FuncionarioApi funcionario, StatusCodigoAcesso status);

	void updateStatusCodigoAcesso(CodigoAcesso codigoAcesso, StatusCodigoAcesso newStatus);

	void cancelaCodigoAcessoPendenteByFuncionario(FuncionarioApi funcionario);

	void validaCodigoAcessoAtivoByFuncionario(FuncionarioApi funcionario);

	Optional<CodigoAcesso> getCodigoAcessoAtivoByFuncionario(FuncionarioApi funcionarioApi);

}
