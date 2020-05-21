package br.com.mundodev.scd.api.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = -4702576409346534942L;
	
	@JsonAlias("id_token")
	private String token;

}
