package br.com.mundodev.scd.api.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.config.AppStatus;
import br.com.mundodev.scd.api.domain.AppResponse;
import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.service.FuncionarioService;

@RestController
@RequestMapping("/codigo-acesso")
public class CodigoAcessoController {
	
	final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private FuncionarioService funcionarioService;
	private CodigoAcessoService codigoAcessoService;

	@Autowired
	public CodigoAcessoController(final FuncionarioService funcionarioService,
			final CodigoAcessoService codigoAcessoService) {
		this.funcionarioService = funcionarioService;
		this.codigoAcessoService = codigoAcessoService;
	}

	@PostMapping("/convenio/{convenio}/login/{login}")
	public ResponseEntity<AppResponse<String>> createPassCode(final @PathVariable Long convenio,
			final @PathVariable String login) {

		logger.info("Recebido requisição para criar código de acesso para convênio: {} e tomador: {}", convenio, login);
		
		final FuncionarioApi funcionario = funcionarioService.getFuncionarioByLogin(login);
		
		logger.info("{}", funcionario);
		
		if (funcionario != null) {
			codigoAcessoService.createCodigoAcesso(funcionario);
		}

		final var appResponse = new AppResponse<String>("", null, AppStatus.SUCCESS);

		final var response = new ResponseEntity<>(appResponse, HttpStatus.OK);

		return response;

	}

}
