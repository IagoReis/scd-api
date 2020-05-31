package br.com.mundodev.scd.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.domain.AppUser;
import br.com.mundodev.scd.api.service.AcessoService;
import br.com.mundodev.scd.api.service.CodigoAcessoService;

@RestController
@RequestMapping("/logout")
public class LogoutController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private CodigoAcessoService codigoAcessoService;
	
	private AcessoService acessoService;

	@Autowired
	private LogoutController(
			final CodigoAcessoService codigoAcessoService,
			final AcessoService acessoService) {
		this.codigoAcessoService = codigoAcessoService;
		this.acessoService = acessoService;
	}
	
	@PutMapping({"", "/"})
	public ResponseEntity<?> cancelAuthenticationToken(
			final @AuthenticationPrincipal AppUser appUser,
			final HttpServletRequest request
		) throws Exception {
		
		logger.info("Recebido requisição para fazer logout do convênio {}, tomador {} e código de acesso {}", appUser.getTomador().getConvenio(), appUser.getTomador(), appUser.getCodigoAcesso());
		
		codigoAcessoService.encerraCodigoAcesso(appUser.getCodigoAcesso());	
		acessoService.encerraAcesso(appUser.getCodigoAcesso(), getClientIp(request));
		
		return ResponseEntity.ok(Boolean.TRUE);
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