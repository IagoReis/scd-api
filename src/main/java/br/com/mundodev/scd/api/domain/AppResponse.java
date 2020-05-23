package br.com.mundodev.scd.api.domain;

import java.io.Serializable;

import br.com.mundodev.scd.api.enumeration.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppResponse<T> implements Serializable {

	private static final long serialVersionUID = 7365457579338007032L;
	
	private String message;	
	private T body;
	private AppStatus status;
	
}
