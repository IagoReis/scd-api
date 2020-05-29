package br.com.mundodev.scd.api.enumeration;

import java.util.Optional;

public enum AuthenticationStringEnum {
	
	CONVENIO(0),
	TOMADOR(1),
	CODIGO_ACESSO(2);
	
	private Integer indice;
	
	AuthenticationStringEnum(final Integer indice) {
		this.indice = indice;
	}
	
	public Integer getIndice() {
		return indice;
	} 
	
	public static Optional<AuthenticationStringEnum> getFromIndice (final Integer indice) {
		for (final AuthenticationStringEnum each : AuthenticationStringEnum.values()) {
			if (each.getIndice().compareTo(indice) == 0) {
				return Optional.of(each);
			}
		}
		
		return Optional.empty();
	}
	
}
