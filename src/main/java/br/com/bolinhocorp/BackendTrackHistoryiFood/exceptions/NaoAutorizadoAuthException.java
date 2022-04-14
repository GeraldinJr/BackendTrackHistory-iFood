package br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions;

public class NaoAutorizadoAuthException extends RuntimeException{
	
	public NaoAutorizadoAuthException(String message) {
		super(message);
	}
}
