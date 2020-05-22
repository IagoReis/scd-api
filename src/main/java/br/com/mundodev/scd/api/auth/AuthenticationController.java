package br.com.mundodev.scd.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mundodev.scd.api.auth.service.AppUserDetailsService;
import br.com.mundodev.scd.api.domain.AuthenticationRequest;
import br.com.mundodev.scd.api.domain.AuthenticationResponse;


@RestController
public class AuthenticationController {
	
	private AuthenticationManager authenticationManager;

	private AppUserDetailsService appUserDetailsService;

	private JwtTokenConfig jwtTokenConfig;

	@Autowired
	private AuthenticationController(final AuthenticationManager authenticationManager,
			final AppUserDetailsService appUserDetailsService, final JwtTokenConfig jwtTokenConfig) {
		this.authenticationManager = authenticationManager;
		this.appUserDetailsService = appUserDetailsService;
		this.jwtTokenConfig = jwtTokenConfig;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			final var usernamePasswordAuthenticationToken = 
					new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getCodigo());

			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch (final BadCredentialsException e) {
			throw new Exception("Username ou login incorreto", e);
		}

		final var userDetails = appUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());

		final String token = jwtTokenConfig.generateToken(userDetails);

		final var authenticationResponse = new AuthenticationResponse(token);

		final var response = ResponseEntity.ok(authenticationResponse);

		return response;
	}
}