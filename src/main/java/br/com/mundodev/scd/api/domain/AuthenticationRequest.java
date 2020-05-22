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
public class AuthenticationRequest implements Serializable 
{
	private static final long serialVersionUID = 8022822076968631298L;
	
	private String login;
	private String codigo;
}