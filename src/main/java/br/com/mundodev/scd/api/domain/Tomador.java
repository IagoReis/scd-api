package br.com.mundodev.scd.api.domain;

import java.io.Serializable;

import br.com.mundodev.scd.api.enumeration.ConvenioEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Tomador implements Serializable {

	private static final long serialVersionUID = 8705300889328659514L;
	
	private ConvenioEnum convenio;
	private String identificacao;
	
	private Long id;
	private String nome;
	private String cpf;
	private String matricula;
	private String login;
	private String email;
	private String celular;
	private String salario;
	private String status;
	private String elegivelConsignado;

}
