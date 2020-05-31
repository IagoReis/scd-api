package br.com.mundodev.scd.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table
@Embeddable
public class CodigoAcessoId implements Serializable {
	
	private static final long serialVersionUID = -5606858805587586424L;

	@NotNull(message = "Convênio é um campo obrigatório")
	@Column(name = "id_convenio", nullable = false)
	private Long idConvenio;
	
	@NotNull(message = "Tomador é um campo obrigatório")
	@Column(name = "id_tomador", nullable = false)
	private Long idTomador;
	
	@NotNull(message = "Código é um campo obrigatório")
	@Column(name = "codigo_acesso", nullable = false)
	private String codigoAcesso;
	
}
