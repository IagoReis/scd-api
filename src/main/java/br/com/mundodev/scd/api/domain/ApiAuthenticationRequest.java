package br.com.mundodev.scd.api.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiAuthenticationRequest implements Serializable {

	private static final long serialVersionUID = -4102486771072980719L;
	
	private String username;
	private String password;

}
