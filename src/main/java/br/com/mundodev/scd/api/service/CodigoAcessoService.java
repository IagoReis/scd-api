package br.com.mundodev.scd.api.service;

import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.model.CodigoAcesso;

public interface CodigoAcessoService {

	CodigoAcesso createCodigoAcesso(FuncionarioApi funcionario);

}
