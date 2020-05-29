package br.com.mundodev.scd.api.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;

@Converter(autoApply = true)
public class StatusCodigoAcessoAttributeConverter implements AttributeConverter<StatusCodigoAcesso, String> {

	@Override
	public String convertToDatabaseColumn(final StatusCodigoAcesso attribute) {
		return attribute.toString();
	}

	@Override
	public StatusCodigoAcesso convertToEntityAttribute(String dbData) {
		return StatusCodigoAcesso.valueOf(dbData);
	}

}
