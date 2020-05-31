package br.com.mundodev.scd.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Entity(name = "acesso")
public class Acesso implements Serializable {
	
	private static final long serialVersionUID = 6038369870001665078L;
	
	@EmbeddedId
	private CodigoAcessoId id;
	
	@CreationTimestamp
	@Column(name = "dt_inicio", nullable = false)
	private LocalDateTime dataInicio;
	
	@Column(name = "ip_inicio", nullable = true)
	private String ipInicio;
	
	@Column(name = "dt_termino", nullable = true)
	private LocalDateTime dataTermino;
	
	@Column(name = "ip_termino", nullable = true)
	private String ipTermino;
	
}
