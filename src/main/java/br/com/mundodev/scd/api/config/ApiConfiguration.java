package br.com.mundodev.scd.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfiguration {
	
	private ApiRestTemplateInterceptor apiRestTemplateInterceptor;

	@Autowired
	public ApiConfiguration(final ApiRestTemplateInterceptor apiRestTemplateInterceptor) {
		this.apiRestTemplateInterceptor = apiRestTemplateInterceptor;
	}
	
	@Bean(name = "apiRestTemplate")
	public RestTemplate getApiRestTemplate() {
		final var restTemplate = new RestTemplate();

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}
		
		interceptors.add(apiRestTemplateInterceptor);
		
		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}

}
