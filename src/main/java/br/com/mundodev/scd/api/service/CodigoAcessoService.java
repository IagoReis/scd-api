package br.com.mundodev.scd.api.service;

import java.util.List;
import java.util.Optional;

import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.model.CodigoAcesso;

public interface CodigoAcessoService {

	CodigoAcesso createCodigoAcesso(Tomador tomador);

	Optional<CodigoAcesso> getCodigoAcessoPendenteByTomador(Tomador tomador);

	List<CodigoAcesso> getCodigoAcessoByTomadorAndStatus(Tomador tomador, StatusCodigoAcesso status);

	void updateStatusCodigoAcesso(CodigoAcesso codigoAcesso, StatusCodigoAcesso newStatus);

	void cancelaCodigoAcessoPendenteByFuncionario(Tomador tomador);

	void validaCodigoAcessoAtivoByFuncionario(Tomador tomador);

	Optional<CodigoAcesso> getCodigoAcessoAtivoByFuncionario(Tomador tomador);

	void updateStatus(CodigoAcesso codigoAcesso, StatusCodigoAcesso status);

	void ativaCodigoAcesso(CodigoAcesso codigoAcesso);
	
	void encerraCodigoAcesso(CodigoAcesso codigoAcesso);

}
