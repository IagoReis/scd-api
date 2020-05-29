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
	
	private Tomador tomador;	
	private CodigoAcesso codigoAcesso;

	public AppUser(final Tomador tomador, final CodigoAcesso codigoAcesso, final Collection<? extends GrantedAuthority> authorities) {
		
		super(String.format("%s:%s:%s", tomador.getConvenio().getId(), tomador.getLogin(), codigoAcesso.getCodigo()), codigoAcesso.getCodigo(), authorities);
	
		this.tomador = tomador;
		this.codigoAcesso = codigoAcesso;
	}
	
	public AppUser(final String username, final String password, final Collection<? extends GrantedAuthority> authorities, final Tomador tomador, final CodigoAcesso codigoAcesso) {
		
		super(username, password, authorities);
	
		this.tomador = tomador;
		this.codigoAcesso = codigoAcesso;
	}

}
