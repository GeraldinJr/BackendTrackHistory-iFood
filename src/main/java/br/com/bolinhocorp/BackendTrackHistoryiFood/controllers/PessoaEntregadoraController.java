package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.TrackHistoryDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;
import br.com.bolinhocorp.BackendTrackHistoryiFood.modelview.PossuiPedidoDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.MethodsUtil;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	private TrackHistoryDAO trackDAO;

	@PostMapping("/pessoa-entregadora/cadastro")
	public ResponseEntity<?> cadastro(@RequestBody PessoaCadastroDTO pessoa) {

		try {
			if(!MethodsUtil.emailIsValid(pessoa.getEmail())) {
				throw new DadosInvalidosException("Infome um e-mail válido");
			}
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

	@GetMapping("/pessoa-entregadora/possui-pedido")
	@Operation(summary = "Verificar se já possui pedido", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> possuiPedido() {
		try {
			int id = MethodsUtil.getIdPessoa();
			TrackHistory track = trackDAO.recuperarUltimoPelaPessoaId(id);
			if (track == null || !track.getPedido().getStatusPedido().equals(Status.EM_ROTA)) return ResponseEntity.ok().body(new PossuiPedidoDTO(false, null));

			return ResponseEntity.ok().body(new PossuiPedidoDTO(track.getPedido()));
		} catch (DadosInvalidosException e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}

}
