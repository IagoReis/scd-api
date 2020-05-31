package br.com.mundodev.scd.api.service;

import java.util.Optional;

import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.model.Acesso;
import br.com.mundodev.scd.api.model.CodigoAcesso;

public interface AcessoService {
	
	Acesso createAcesso(Tomador tomador, CodigoAcesso codigoAcesso);
	
	Acesso createAcesso(Tomador tomador, CodigoAcesso codigoAcesso, String ipAdress);
	
	Optional<Acesso> getAcessoNaoEncerrado(Tomador tomador);

	void encerraAcesso(CodigoAcesso codigoAcesso, String ipAdress);
	
}
