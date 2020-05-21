package br.com.mundodev.scd.api.service;

import br.com.mundodev.scd.api.domain.ApiAuthenticationResponse;

public interface ApiAuthIntegration {

	ApiAuthenticationResponse getTokenAuthorization();
	
}
