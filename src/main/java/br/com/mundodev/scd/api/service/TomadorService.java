package br.com.mundodev.scd.api.service;

import java.util.Optional;

import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.enumeration.ConvenioEnum;

public interface TomadorService {

	Optional<Tomador> getTomador(ConvenioEnum convenioEnum, String identificacao);

}
