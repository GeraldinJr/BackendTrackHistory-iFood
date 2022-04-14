package br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;

@ControllerAdvice
public class HandlingExceptions {
	
	@ExceptionHandler(value = {NaoAutorizadoAuthException.class})
	public ResponseEntity<?> handleNaoAutorizadoAuthException(NaoAutorizadoAuthException e){
		Message message = new Message(e.getMessage());
		HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
		return new ResponseEntity<>(message, unauthorized);
	}
	@ExceptionHandler(value = {NaoAutorizadoException.class})
	public ResponseEntity<?> handleNaoAutorizadoException(NaoAutorizadoException e){
		Message message = new Message(e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = {DadosInvalidosException.class})
	public ResponseEntity<?> handleDadosInvalidosException(DadosInvalidosException e){
		Message message = new Message(e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ErroInternoException.class})
	public ResponseEntity<?> handleErroInternoException(ErroInternoException e){
		return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(value = {NaoEncontradoException.class})
	public ResponseEntity<?> handleNaoEncontradoException(NaoEncontradoException e){
		return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = {PedidoIndisponivelException.class})
	public ResponseEntity<?> handlePedidoIndisponivelException(PedidoIndisponivelException e){
		return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
