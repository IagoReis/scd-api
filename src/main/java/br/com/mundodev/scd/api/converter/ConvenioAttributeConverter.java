package br.com.mundodev.scd.api.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.mundodev.scd.api.enumeration.ConvenioEnum;

@Converter(autoApply = true)
public class ConvenioAttributeConverter implements AttributeConverter<ConvenioEnum, String> {

	@Override
	public String convertToDatabaseColumn(final ConvenioEnum attribute) {
		return attribute.getId().toString();
	}

	@Override
	public ConvenioEnum convertToEntityAttribute(String dbData) {
		return ConvenioEnum.TESTE;
		/*
		for ( ConvenioEnum convenioEnum : ConvenioEnum.values() ) {
			if (convenioEnum.getId().equals(Long.valueOf(dbData))) {
				return convenioEnum;
			}
		}
		
		return null;*/
	}

}
