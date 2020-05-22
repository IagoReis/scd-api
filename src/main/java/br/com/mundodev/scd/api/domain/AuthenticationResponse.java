package br.com.mundodev.scd.api.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthenticationResponse implements Serializable 
{
	private static final long serialVersionUID = -7717444142488782666L;

	private String token;
}