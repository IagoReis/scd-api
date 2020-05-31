package br.com.mundodev.scd.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import br.com.mundodev.scd.api.exception.ExisteSessaoAtivaException;
import br.com.mundodev.scd.api.service.AcessoService;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.service.TomadorService;

@RestController
@RequestMapping("/codigo-acesso")
public class CodigoAcessoController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private TomadorService tomadorService;
	private CodigoAcessoService codigoAcessoService;
	private AcessoService acessoService;

	@Autowired
	public CodigoAcessoController(
			final TomadorService tomadorService,
			final CodigoAcessoService codigoAcessoService,
			final AcessoService acessoService
		){
		this.tomadorService = tomadorService;
		this.codigoAcessoService = codigoAcessoService;
		this.acessoService = acessoService;
	}

	@PostMapping("/convenio/{idConvenio}/tomador/{identificacaoTomador}")
	public ResponseEntity<AppResponse<String>> createPassCode(
			final @PathVariable("idConvenio") ConvenioEnum convenio,
			final @PathVariable String identificacaoTomador,
			final HttpServletRequest request
		) {
		
		logger.info("Recebido requisição para criar código de acesso para o convênio {} e tomador: {}", convenio, identificacaoTomador);
		
		final Optional<Tomador> tomadorOptional = tomadorService.getTomador(convenio, identificacaoTomador);
		
		logger.info("{}", tomadorOptional);
		
		if (tomadorOptional.isPresent()) {
			
			final var tomador = tomadorOptional.get();
			
			final var codigoAcessoAtivo = codigoAcessoService.getCodigoAcessoAtivo(tomador);
			
			final var codigoAcessoPendente = codigoAcessoService.getCodigoAcessoPendente(tomador);
			
			final var acessoNaoEncerrado = acessoService.getAcessoNaoEncerrado(tomador);
			
			if (codigoAcessoAtivo.isPresent() || codigoAcessoPendente.isPresent() || acessoNaoEncerrado.isPresent()) {
				logger.error("Tomador {} possui sessão não encerrada", tomador);
				throw new ExisteSessaoAtivaException("Tomador possui sessão não encerrada");
			}
			
			codigoAcessoService.createCodigoAcesso(tomador, getClientIp(request));
		}
		
		final var appResponse = new AppResponse<String>(AppStatus.SUCCESS.toString(), AppStatus.SUCCESS.toString(), AppStatus.SUCCESS);
		
		final var response = new ResponseEntity<>(appResponse, HttpStatus.OK);
		
		return response;
		
	}
	
	private String getClientIp(final HttpServletRequest request) {
		
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}

}

