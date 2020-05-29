package br.com.mundodev.scd.api.auth;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.AppUser;
import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.enumeration.AuthenticationStringEnum;
import br.com.mundodev.scd.api.enumeration.ConvenioEnum;
import br.com.mundodev.scd.api.model.CodigoAcesso;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.service.TomadorService;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private TomadorService tomadorService;
	
	@Autowired
	private CodigoAcessoService codigoAcessoService;
	
	@Autowired
	private JwtTokenConfig jwtTokenConfig;;

	@Override
	public AppUser loadUserByUsername(final String authData) throws UsernameNotFoundException {
		
		final var authenticationData = jwtTokenConfig.getAuthenticationDataFromString(authData);
		final ConvenioEnum convenio = ConvenioEnum.fromId(authenticationData.get().get(AuthenticationStringEnum.CONVENIO).get());
		final String tomador = authenticationData.get().get(AuthenticationStringEnum.TOMADOR).get();
		
		final Optional<Tomador> tomadorOptional = tomadorService.getTomador(convenio, tomador);
		
		if (tomadorOptional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		final Optional<CodigoAcesso> codigoAcessoPendenteOptional = codigoAcessoService.getCodigoAcessoPendenteByTomador(tomadorOptional.get());
		final Optional<CodigoAcesso> codigoAcessoAtivoOptional = codigoAcessoService.getCodigoAcessoAtivoByFuncionario(tomadorOptional.get());
		
		if (codigoAcessoAtivoOptional.isEmpty() && codigoAcessoPendenteOptional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		final var codigoAcesso = codigoAcessoAtivoOptional.isPresent() ? codigoAcessoAtivoOptional.get() : codigoAcessoPendenteOptional.get();
		
		final var user = new AppUser(authData, codigoAcesso.getCodigo(), new HashSet<>(), tomadorOptional.get(), codigoAcesso);
		
		return user;
	}
	
}
