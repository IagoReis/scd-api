package br.com.mundodev.scd.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.auth.AppUserDetailsService;
import br.com.mundodev.scd.api.auth.JwtTokenConfig;
import br.com.mundodev.scd.api.domain.AppUser;
import br.com.mundodev.scd.api.domain.AuthenticationRequest;
import br.com.mundodev.scd.api.domain.AuthenticationResponse;
import br.com.mundodev.scd.api.enumeration.ConvenioEnum;
import br.com.mundodev.scd.api.service.CodigoAcessoService;

@RestController
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private AuthenticationManager authenticationManager;

	private AppUserDetailsService appUserDetailsService;
	
	private JwtTokenConfig jwtTokenConfig;
	
	private CodigoAcessoService codigoAcessoService;

	@Autowired
	private LoginController(
			final AuthenticationManager authenticationManager,
			final AppUserDetailsService appUserDetailsService,
			final JwtTokenConfig jwtTokenConfig,
			final CodigoAcessoService codigoAcessoService) {
		this.authenticationManager = authenticationManager;
		this.appUserDetailsService = appUserDetailsService;
		this.jwtTokenConfig = jwtTokenConfig;
		this.codigoAcessoService = codigoAcessoService;
	}

	@PostMapping("/login/convenio/{idConvenio}/tomador/{tomador}")
	public ResponseEntity<?> createAuthenticationToken(final @PathVariable("idConvenio") ConvenioEnum convenio, final @PathVariable String tomador, @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		logger.info("Recebido requisição para fazer login (criar token) para o convênio {}, tomador {} e authentication request {}", convenio, tomador, authenticationRequest);
		
		final String authData = convenio.getId().toString().concat(":").concat(tomador).concat(":").concat(authenticationRequest.getCodigoAcesso());
		
		try {
			final var usernamePasswordAuthenticationToken = 
					new UsernamePasswordAuthenticationToken(authData, authenticationRequest.getCodigoAcesso());

			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch (final BadCredentialsException e) {
			throw new Exception("Username ou login incorreto", e);
		}

		final var userDetails = appUserDetailsService.loadUserByUsername(authData);

		final String token = jwtTokenConfig.generateToken(userDetails);

		final var authenticationResponse = new AuthenticationResponse(token);

		final var response = ResponseEntity.ok(authenticationResponse);
		
		codigoAcessoService.ativaCodigoAcesso(userDetails.getCodigoAcesso());

		return response;
	}
	
	@PutMapping(value = {"/logout", "/logout/"})
	public ResponseEntity<?> cancelAuthenticationToken(final @AuthenticationPrincipal AppUser appUser) throws Exception {
		
		logger.info("Recebido requisição para fazer logout do convênio {}, tomador {} e código de acesso {}", appUser.getTomador().getConvenio(), appUser.getTomador(), appUser.getCodigoAcesso());
		
		codigoAcessoService.encerraCodigoAcesso(appUser.getCodigoAcesso());
		
		return ResponseEntity.ok(Boolean.TRUE);
	}
	
}