package br.com.mundodev.scd.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
	
	private static final long serialVersionUID = 9142149358160352241L;
	
	@EmbeddedId
	private CodigoAcessoId id;
	
	@Column(name = "ip", nullable = true)
	private String ip;
	
	@Column(name = "destinatario", nullable = false)
	private String destinatario;
	
	@NotNull(message = "Status é um campo obrigatório")
	@Column(name = "status", nullable = false)
	private StatusCodigoAcesso statusCodigoAcesso;
	
	@CreationTimestamp
	@Column(name = "dt_geracao", nullable = false)
	private LocalDateTime dataGeracao;
	
	@Column(name = "dt_expiracao", nullable = false)
	private LocalDateTime dataExpiracao;
	
}
