package br.com.mundodev.scd.api.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.mundodev.scd.api.model.CodigoAcesso;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AppUser extends User {

	private static final long serialVersionUID = -3073491871097658141L;
	
	private FuncionarioApi funcionario;
	private CodigoAcesso codigoAcesso;

	public AppUser(final FuncionarioApi funcionario, final CodigoAcesso codigoAcesso, final Collection<? extends GrantedAuthority> authorities) {
		super(funcionario.getLogin(), codigoAcesso.getCodigo(), authorities);
		
		this.funcionario = funcionario;
		this.codigoAcesso = codigoAcesso;
	}

}
