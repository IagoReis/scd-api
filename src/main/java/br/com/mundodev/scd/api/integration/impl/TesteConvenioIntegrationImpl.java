package br.com.mundodev.scd.api.integration.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.domain.TomadorDtoTeste;
import br.com.mundodev.scd.api.enumeration.ConvenioEnum;
import br.com.mundodev.scd.api.integration.ConvenioIntegration;
import br.com.mundodev.scd.api.utils.AppUtils;

@Service
public class TesteConvenioIntegrationImpl implements ConvenioIntegration {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static String API_URL = "https://ereceb.herokuapp.com/api";
	
	private final static String API_URL_TOMADOR_BY_LOGIN = API_URL.concat("/funcionarios/login/{login}");
	
	private final static String API_URL_TOMADOR_BY_EMAIL = API_URL.concat("/assetanywhere/funcionarios/identificacao/{email}");
	
	private final static String API_URL_TOMADOR_BY_CELULAR = API_URL.concat("/assetanywhere/funcionarios/identificacao/{celular}");
	
	private RestTemplate apiRestTemplate;
	
	@Autowired
	public TesteConvenioIntegrationImpl(final RestTemplate apiRestTemplate) {
		this.apiRestTemplate = apiRestTemplate;
	}
	
	@Override
	public Tomador getTomador(final String identificacao) {
		
		logger.info("Buscando tomador de forma genérica através da identificação {}", identificacao);
		
		if (AppUtils.isValidEmail(identificacao)) {
			return getTomadorByEmail(identificacao);
		}
		
		if (AppUtils.isCelular(identificacao)) {
			return getTomadorByCelular(identificacao);
		}
		
		return null;
	}
	
	@Override
	public Tomador getTomadorByLogin(final String login) {
		
		logger.info("Buscando tomador de através do login {}", login);
		
		final ResponseEntity<TomadorDtoTeste> responseEntity = apiRestTemplate.getForEntity(API_URL_TOMADOR_BY_LOGIN, TomadorDtoTeste.class, login);
		
		final var tomadorDtoTeste = responseEntity.getBody();
		
		final var tomador = extract(tomadorDtoTeste);
		
		logger.info("Retornando o tomador {}", tomador);
		
		return tomador;
	}
	
	@Override
	public Tomador getTomadorByEmail(final String email) {
		
		logger.info("Buscando tomador de através do e-mail {}", email);
		
		final ResponseEntity<TomadorDtoTeste> responseEntity = apiRestTemplate.getForEntity(API_URL_TOMADOR_BY_EMAIL, TomadorDtoTeste.class, email);
		
		final var tomadorDtoTeste = responseEntity.getBody();
		
		final var tomador = extract(tomadorDtoTeste);
		
		logger.info("Retornando o tomador {}", tomador);
		
		return tomador;
	}
	
	@Override
	public Tomador getTomadorByCelular(final String celular) {
		
		logger.info("Buscando tomador de através do celular {}", celular);
		
		final ResponseEntity<TomadorDtoTeste> responseEntity = apiRestTemplate.getForEntity(API_URL_TOMADOR_BY_CELULAR, TomadorDtoTeste.class, celular);
		
		final var tomadorDtoTeste = responseEntity.getBody();
		
		final var tomador = extract(tomadorDtoTeste);
		
		logger.info("Retornando o tomador {}", tomador);
		
		return tomador;
	}
	
	@Override
	public Tomador getTomadorByMatricula(final String matricula) {
		
		logger.info("Buscando tomador de através da matrícula {}", matricula);
		
		return null;
	}
	
	private Tomador extract(final TomadorDtoTeste tomadorDtoTeste) {
		
		final Tomador tomador = new Tomador();
		
		tomador.setConvenio(ConvenioEnum.TESTE);
		tomador.setId(tomadorDtoTeste.getId());
		tomador.setLogin(tomadorDtoTeste.getLogin());
		tomador.setNome(tomadorDtoTeste.getNome());
		tomador.setEmail(tomadorDtoTeste.getEmail());
		tomador.setCelular(tomadorDtoTeste.getCelular());
		
		return tomador;
	}

}
