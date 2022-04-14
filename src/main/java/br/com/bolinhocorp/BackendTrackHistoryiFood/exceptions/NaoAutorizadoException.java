package br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions;

public class NaoAutorizadoException extends RuntimeException{
	public NaoAutorizadoException(String message) {
		super(message);
	}
}
