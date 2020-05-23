package br.com.mundodev.scd.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.domain.AppUser;
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
	
	@GetMapping("/")
	public ResponseEntity<FuncionarioApi> teste(final @AuthenticationPrincipal AppUser appUser) {
		
		return ResponseEntity.ok(appUser.getFuncionario());
		
	}
	
}
