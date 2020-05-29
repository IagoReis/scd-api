package br.com.mundodev.scd.api.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.enumeration.ConvenioEnum;
import br.com.mundodev.scd.api.integration.ConvenioIntegration;
import br.com.mundodev.scd.api.service.TomadorService;

@Service
public class TomadorServiceImpl implements TomadorService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, ConvenioIntegration> convenioIntegrations = new HashMap<>();
	
	@Autowired
	public TomadorServiceImpl(final Map<String, ConvenioIntegration> convenioIntegrations) {
		this.convenioIntegrations = convenioIntegrations;
	}
	
	@Override
	public Optional<Tomador> getTomador(final ConvenioEnum convenio, final String identificacao) {
		
		logger.info("Buscando dados do tomador através do convênio {} e identificação {}", convenio, identificacao);
		
		final Tomador tomador = convenioIntegrations.get(convenio.getIntegrationName()).getTomador(identificacao);
		
		final Optional<Tomador> result = tomador != null ? Optional.of(tomador) : Optional.empty();
		
		return result;
	}
	
}
