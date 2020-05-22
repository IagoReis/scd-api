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

import org.hibernate.annotations.CreationTimestamp;

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

	private static final String SEQUENCE_NAME = "";
	
	private static final long serialVersionUID = 9142149358160352241L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQUENCE_NAME)
	@SequenceGenerator(sequenceName = "codigo_acesso_id_seq", name = SEQUENCE_NAME, initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "id_convenio", nullable = false)
	private Long idConvenio;
	
	@Column(name = "id_tomador", nullable = false)
	private Long idTomador;
	
	@Column(name = "codigo", nullable = false)
	private String codigo;
	
	@Column(name = "tp_situacao", nullable = false)
	private String tipoSituacao;
	
	@CreationTimestamp
	@Column(name = "dt_geracao", nullable = false)
	private LocalDateTime dataGeracao;
	
	@Column(name = "dt_expiracao", nullable = false)
	private LocalDateTime dataExpiracao;
	
}
