package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.security.Token;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;

@RestController
@CrossOrigin("*")
public class LoginController {
	
	@Autowired
	private IPessoaEntregadora service;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody PessoaLoginDTO dadosLogin){
		
		if(dadosLogin.getEmail()==null || dadosLogin.getSenha() == null) {
			return ResponseEntity.badRequest().body(new Message("Favor, informar e-mail e senha"));
		}
		
		Token token = service.gerarTokenUsuarioLogado(dadosLogin);
		
		if(token == null) {
			return ResponseEntity.status(401).body(new Message("E-mail e/ou senha incorreto(s)"));
		}
		
		return ResponseEntity.ok(token);
	}
}
