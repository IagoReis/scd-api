package br.com.mundodev.scd.api.auth.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.auth.AppUser;
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
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final FuncionarioApi funcionario = funcionarioService.getFuncionarioByLogin(username);
		
		if (funcionario == null || funcionario.getId() == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		final CodigoAcesso codigoAcesso = codigoAcessoService.getByFuncionario(funcionario);
		
		if (codigoAcesso == null || codigoAcesso.getCodigo() == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		final var user = new AppUser(funcionario, codigoAcesso, new HashSet<>());

		return user;
	}
}
