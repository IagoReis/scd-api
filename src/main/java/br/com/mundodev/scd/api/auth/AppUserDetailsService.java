package br.com.mundodev.scd.api.auth;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.AppUser;
import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.model.CodigoAcesso;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.service.FuncionarioService;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private CodigoAcessoService codigoAcessoService;

	@Override
	public AppUser loadUserByUsername(final String login) throws UsernameNotFoundException {
		
		final Optional<FuncionarioApi> funcionarioOptional = funcionarioService.getFuncionarioByLogin(login);
		
		if (funcionarioOptional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		final Optional<CodigoAcesso> codigoAcessoPendenteOptional = codigoAcessoService.getCodigoAcessoPendenteByFuncionario(funcionarioOptional.get());
		final Optional<CodigoAcesso> codigoAcessoAtivoOptional = codigoAcessoService.getCodigoAcessoAtivoByFuncionario(funcionarioOptional.get());
		
		if (codigoAcessoAtivoOptional.isEmpty() && codigoAcessoPendenteOptional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		final var codigoAcesso = codigoAcessoAtivoOptional.isPresent() ? codigoAcessoAtivoOptional.get() : codigoAcessoPendenteOptional.get();
		
		final var user = new AppUser(funcionarioOptional.get(), codigoAcesso, new HashSet<>());
		
		return user;
	}

	public AppUser loadUserByUsernameOnly(String username) {
		
		final Optional<FuncionarioApi> funcionarioOptional = funcionarioService.getFuncionarioByLogin(username);
		
		if (funcionarioOptional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		final var user = new AppUser(funcionarioOptional.get(), new CodigoAcesso(), new HashSet<>());
		
		return user;
	}
}
