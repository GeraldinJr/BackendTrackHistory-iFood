package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.TokenENomeDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.MethodsUtil;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private IPessoaEntregadora service;

	@PostMapping("/pessoa-entregadora/login")
	@Operation(summary = "Logar pessoa entregadora", description = "Este endpoint faz a autenticação da pessoa entregadora já cadastrada na base de dados, e retorna um token de autenticação para acesso aos endpoints protegidos.")
	public ResponseEntity<?> login(@RequestBody PessoaLoginDTO dadosLogin) {

		try {
			if (dadosLogin.getEmail() == null || dadosLogin.getSenha() == null) {
				throw new DadosInvalidosException("Favor informar e-mail e senha");
			}

			if(!MethodsUtil.emailIsValid(dadosLogin.getEmail())) {
				throw new DadosInvalidosException("Infome um e-mail válido");
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
