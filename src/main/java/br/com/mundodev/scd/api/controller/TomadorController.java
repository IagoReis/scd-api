package br.com.mundodev.scd.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.domain.AppUser;
import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.service.TomadorService;

@RestController
@RequestMapping("/tomador")
public class TomadorController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unused")
	private TomadorService tomadorService; 
	
	@Autowired
	public TomadorController (final TomadorService tomadorService) {
		this.tomadorService = tomadorService;	
	}
	
	@GetMapping(value = {"", "/"})
	public ResponseEntity<Tomador> teste(final @AuthenticationPrincipal AppUser appUser) {
		
		logger.info("Recebido requisição para retornar dados do tomador {}", appUser.getTomador());
		
		return ResponseEntity.ok(appUser.getTomador());		
	}
	
}
