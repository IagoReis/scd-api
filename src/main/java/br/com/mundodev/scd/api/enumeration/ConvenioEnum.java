package br.com.mundodev.scd.api.enumeration;

import br.com.mundodev.scd.api.domain.Convenio;

public enum ConvenioEnum {
	
	TESTE(1L, new Convenio(1L, "Convênio de Teste"), "testeConvenioIntegrationImpl");
	
	private Long id;
	private Convenio convenio;
	private String integrationName;
	
	
	ConvenioEnum(final Long id, final Convenio convenio, final String integrationName) {
		this.id = id;
		this.convenio = convenio;
		this.integrationName = integrationName;
	}
	
	public static ConvenioEnum fromId(final Long id) {
		for ( ConvenioEnum convenioEnum : ConvenioEnum.values() ) {
			if (convenioEnum.getId().equals(id)) {
				return convenioEnum;
			}
		}
		
		return null;
	}
	
	public static ConvenioEnum fromId(final String id) {
		return fromId(Long.valueOf(id));
	}
	
	public Long getId() {
		return id;
	}
	
	public Convenio getConvenio() {
		return convenio;
	}
	
	public String getIntegrationName() {
		return integrationName;
	}
	
}
