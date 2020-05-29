package br.com.mundodev.scd.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.mundodev.scd.api.enumeration.ConvenioEnum;

@Component
public class ConvenioEnumStringConverter implements Converter<String, ConvenioEnum> {
    @Override
    public ConvenioEnum convert(String value) {
        final var convenioEnum = ConvenioEnum.fromId(value) != null ? ConvenioEnum.fromId(value) : ConvenioEnum.valueOf(value);
        return convenioEnum;
    }
}
