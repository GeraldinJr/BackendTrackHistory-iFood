package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaCadastradaDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaCadastroDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;

@RestController
@CrossOrigin("*")
public class PessoaEntregadoraController {

	@Autowired
	private IPessoaEntregadora service;

	@PostMapping("/pessoa-entregadora/cadastro")
	public ResponseEntity<?> cadastro(@RequestBody PessoaCadastroDTO pessoa) {

		try {

			PessoaEntregadora pessoaJaExiste = service.recuperarPorEmail(pessoa.getEmail());

			if (pessoaJaExiste != null) {
				return ResponseEntity.badRequest().body(new Message("Nao foi possivel cadastrar"));
			}

			if (!pessoa.getSenha().equals(pessoa.getConfirmacao_senha())) {
				return ResponseEntity.badRequest().body(new Message("Campos de senha diferentes"));
			}

			PessoaEntregadora pessoaCadastrada = service.CadastrarPessoaEntregadora(new PessoaEntregadora(pessoa));

			return ResponseEntity.status(201).body(new PessoaCadastradaDTO(pessoaCadastrada));

		} catch (DadosInvalidosException e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));

		}
	}

}
