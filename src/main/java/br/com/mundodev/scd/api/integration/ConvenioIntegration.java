package br.com.mundodev.scd.api.integration;

import br.com.mundodev.scd.api.domain.Tomador;

public interface ConvenioIntegration {

	Tomador getTomadorByLogin(String login);

	Tomador getTomadorByCelular(String celular);

	Tomador getTomadorByEmail(String celular);

	Tomador getTomadorByMatricula(String matricula);

	Tomador getTomador(String identificacao);
	
}
