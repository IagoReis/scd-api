package br.com.mundodev.scd.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table
@Entity(name = "codigo_acesso")
public class CodigoAcesso implements Serializable {
	
	private static final String SEQUENCE_NAME = "cust_codigo_acesso_id_seq";
	
	private static final long serialVersionUID = 9142149358160352241L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQUENCE_NAME)
	@SequenceGenerator(sequenceName = "codigo_acesso_id_seq", name = SEQUENCE_NAME, initialValue = 1, allocationSize = 1)
	private Long id;
	
	@NotNull(message = "Convênio é um campo obrigatório")
	@Column(name = "id_convenio", nullable = false)
	private Long idConvenio;
	
	@NotNull(message = "Tomador é um campo obrigatório")
	@Column(name = "id_tomador", nullable = false)
	private Long idTomador;
	
	@NotNull(message = "Código é um campo obrigatório")
	@Column(name = "codigo", nullable = false)
	private String codigo;
	
	@NotNull(message = "Status é um campo obrigatório")
	@Column(name = "status", nullable = false)
	private StatusCodigoAcesso status;
	
	@CreationTimestamp
	@Column(name = "dt_geracao", nullable = false)
	private LocalDateTime dataGeracao;
	
	@Column(name = "dt_expiracao", nullable = false)
	private LocalDateTime dataExpiracao;
	
}
