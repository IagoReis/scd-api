package br.com.mundodev.scd.api.exception;

public class TokenJaUtilizadoException extends RuntimeException {

	private static final long serialVersionUID = 5430127529596511021L;

	public TokenJaUtilizadoException (final String message) {
		super(message);
	}

}
