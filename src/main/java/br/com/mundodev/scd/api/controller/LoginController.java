package br.com.mundodev.scd.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.auth.AppUserDetailsService;
import br.com.mundodev.scd.api.auth.JwtTokenConfig;
import br.com.mundodev.scd.api.domain.AppResponse;
import br.com.mundodev.scd.api.domain.AuthenticationRequest;
import br.com.mundodev.scd.api.domain.AuthenticationResponse;
import br.com.mundodev.scd.api.enumeration.AppStatus;
import br.com.mundodev.scd.api.enumeration.ConvenioEnum;
import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.exception.TokenJaUtilizadoException;
import br.com.mundodev.scd.api.service.AcessoService;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.service.TomadorService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private AuthenticationManager authenticationManager;

	private AppUserDetailsService appUserDetailsService;
	
	private JwtTokenConfig jwtTokenConfig;
	
	private CodigoAcessoService codigoAcessoService;
	
	private TomadorService tomadorService;
	
	private AcessoService acessoService;

	@Autowired
	private LoginController(
			final AuthenticationManager authenticationManager,
			final AppUserDetailsService appUserDetailsService,
			final JwtTokenConfig jwtTokenConfig,
			final CodigoAcessoService codigoAcessoService,
			final TomadorService tomadorService,
			final AcessoService acessoService
			) {
		this.authenticationManager = authenticationManager;
		this.appUserDetailsService = appUserDetailsService;
		this.jwtTokenConfig = jwtTokenConfig;
		this.codigoAcessoService = codigoAcessoService;
		this.tomadorService = tomadorService;
		this.acessoService = acessoService;
	}

	@GetMapping("/convenio/{idConvenio}/tomador/{tomador}")
	public ResponseEntity<?> getAuthenticationToken(
			final @PathVariable("idConvenio") ConvenioEnum convenio,
			final @PathVariable("tomador") String identificacaoTomador
		) throws Exception {
		
		logger.info("Recebido requisição para verificar se há sessão ativa (código de acesso ativo) para o convênio {} e tomador {}", convenio, identificacaoTomador);
		
		final var tomador = tomadorService.getTomador(convenio, identificacaoTomador);
		
		AppResponse<Boolean> responseObject = null;; 
		
		if (tomador.isPresent()) {
			final var codigoAcesso = codigoAcessoService.getCodigoAcessoAtivo(tomador.get());
			
			if (codigoAcesso.isPresent()) {
				responseObject = new AppResponse<Boolean>(AppStatus.SUCCESS.toString(), Boolean.TRUE, AppStatus.SUCCESS);
			}
			else {
				responseObject = new AppResponse<Boolean>(AppStatus.SUCCESS.toString(), Boolean.FALSE, AppStatus.SUCCESS);
			}			
		}
		else {
			responseObject = new AppResponse<Boolean>(AppStatus.SUCCESS.toString(), Boolean.FALSE, AppStatus.SUCCESS);
		}		
		
		final var response = ResponseEntity.ok(responseObject);

		return response;
	}
	
	@PostMapping("/convenio/{idConvenio}/tomador/{tomador}")
	public ResponseEntity<?> createAuthenticationToken(
			final @PathVariable("idConvenio") ConvenioEnum convenio,
			final @PathVariable String tomador,
			@RequestBody AuthenticationRequest authenticationRequest,
			final HttpServletRequest request
		) throws Exception {
		
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
		
		if (!userDetails.getCodigoAcesso().getStatusCodigoAcesso().equals(StatusCodigoAcesso.PENDENTE)) {
			throw new TokenJaUtilizadoException("O código de acesso informado já foi utilizado");
		}
		
		final String token = jwtTokenConfig.generateToken(userDetails);
		
		final var authenticationResponse = new AuthenticationResponse(token);
		
		acessoService.createAcesso(userDetails.getTomador(), userDetails.getCodigoAcesso(), getClientIp(request));
		
		final var response = ResponseEntity.ok(authenticationResponse);
		
		codigoAcessoService.ativaCodigoAcesso(userDetails.getCodigoAcesso());
		
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