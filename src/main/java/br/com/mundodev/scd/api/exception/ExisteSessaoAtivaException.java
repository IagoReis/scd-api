package br.com.mundodev.scd.api.exception;

public class ExisteSessaoAtivaException extends RuntimeException {

	private static final long serialVersionUID = -2203584436096272974L;
	
	public ExisteSessaoAtivaException (final String message) {
		super(message);
	}

}
