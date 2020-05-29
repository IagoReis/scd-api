package br.com.mundodev.scd.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.domain.AppResponse;
import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.enumeration.AppStatus;
import br.com.mundodev.scd.api.enumeration.ConvenioEnum;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.service.TomadorService;

@RestController
@RequestMapping("/codigo-acesso")
public class CodigoAcessoController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private TomadorService tomadorService;
	private CodigoAcessoService codigoAcessoService;

	@Autowired
	public CodigoAcessoController(
			final TomadorService tomadorService,
			final CodigoAcessoService codigoAcessoService
		){
		this.tomadorService = tomadorService;
		this.codigoAcessoService = codigoAcessoService;
	}

	@PostMapping("/convenio/{idConvenio}/tomador/{tomador}")
	public ResponseEntity<AppResponse<String>> createPassCode(final @PathVariable("idConvenio") ConvenioEnum convenio, final @PathVariable String tomador) {
		
		logger.info("Recebido requisição para criar código de acesso para o convênio {} e tomador: {}", convenio, tomador);
		
		final Optional<Tomador> tomadorOptional = tomadorService.getTomador(convenio, tomador);
		
		logger.info("{}", tomadorOptional);
		
		if (tomadorOptional.isPresent()) {
			codigoAcessoService.createCodigoAcesso(tomadorOptional.get());
		}
		
		final var appResponse = new AppResponse<String>("", null, AppStatus.SUCCESS);
		
		final var response = new ResponseEntity<>(appResponse, HttpStatus.OK);
		
		return response;
		
	}

}
