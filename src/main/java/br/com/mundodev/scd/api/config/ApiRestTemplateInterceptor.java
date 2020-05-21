package br.com.mundodev.scd.api.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import br.com.mundodev.scd.api.domain.ApiAuthenticationResponse;
import br.com.mundodev.scd.api.service.ApiAuthIntegration;

@Component
public class ApiRestTemplateInterceptor implements ClientHttpRequestInterceptor {
	
	private ApiAuthIntegration apiAuthIntegration;
	
	@Autowired
	public ApiRestTemplateInterceptor(final ApiAuthIntegration apiAuthIntegration) {
		this.apiAuthIntegration = apiAuthIntegration;
	}

	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		HttpHeaders headers = request.getHeaders();
		
		if (headers == null) {
			headers = new HttpHeaders();
		}
		
		final ApiAuthenticationResponse tokenAuthorization = apiAuthIntegration.getTokenAuthorization();
		
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", tokenAuthorization.getToken()));
		
		final ClientHttpResponse response = execution.execute(request, body);
		
		return response;
	}

}
