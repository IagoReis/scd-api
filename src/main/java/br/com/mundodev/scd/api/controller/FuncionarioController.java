package br.com.mundodev.scd.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.service.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
	
	private FuncionarioService funcionarioService; 
	
	@Autowired
	public FuncionarioController (final FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;	
	}
	
	@GetMapping("/login/{login}")
	public ResponseEntity<FuncionarioApi> teste(final @PathVariable String login) {
		
		final var funcionario = funcionarioService.getFuncionarioByLogin(login);
		
		return ResponseEntity.ok(funcionario);
	}
	
}
