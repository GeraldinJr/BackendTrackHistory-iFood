package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.TokenENomeDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;

@RestController
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private IPessoaEntregadora service;

	@GetMapping("/")
	public ResponseEntity<?> home() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return ResponseEntity.ok("Bem vindo");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody PessoaLoginDTO dadosLogin) {

		try {
			if (dadosLogin.getEmail() == null || dadosLogin.getSenha() == null) {
				throw new DadosInvalidosException("Favor informar e-mail e senha");
			}

			TokenENomeDTO token = service.gerarTokenUsuarioLogado(dadosLogin);

			if (token == null) {
				return ResponseEntity.status(401).body(new Message("E-mail e/ou senha incorreto(s)"));
			}

			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}
}
