package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import java.util.List;
import java.util.Optional;

import br.com.bolinhocorp.BackendTrackHistoryiFood.util.PedidosPaginados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.PedidoDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPedidoService;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.ITrackHistoryService;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.MethodsUtil;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin("*")
public class PedidosController {
	
	@Autowired
	private PedidoDAO dao;

	@Autowired
	private IPedidoService servicePedido;

	@Autowired
	private IPessoaEntregadora servicePessoa;

	@Autowired
	private ITrackHistoryService serviceTrack;
	
	@GetMapping("/pedidos")
	@Operation(summary = "Carregar todos os pedidos", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> recuperarTodos(@RequestParam Optional<Integer> numeroPagina, @RequestParam Optional<Integer> tamanhoPagina){
		try {
			if(numeroPagina.isPresent()) {
				if( numeroPagina.get() <= 0) throw new DadosInvalidosException("Parâmetros de paginação inválidos");
			}

			if(tamanhoPagina.isPresent()) {
				if( tamanhoPagina.get() <= 0) throw new DadosInvalidosException("Parâmetros de paginação inválidos");
			}

			Integer total = dao.total();
			Integer numPag = numeroPagina.orElseGet(() -> 1);
			Integer tamPag = tamanhoPagina.orElseGet(() -> 10);
			Integer offset = (numPag -1) * tamPag;
			if (offset > total) throw new DadosInvalidosException("A Página selecionada não existe, selecione outra página ou altere o tamanho dessa");

			PedidosPaginados p = new PedidosPaginados(dao.paginaPedido(offset, tamPag), total, numPag, tamPag);
			return ResponseEntity.ok().body(p);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}

	@GetMapping("/pedidos/em-aberto")
	@Operation(summary = "Carregar pedidos em aberto", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> recuperarPedidosEmAberto(@RequestParam Optional<Integer> numeroPagina, @RequestParam Optional<Integer> tamanhoPagina) {
		try {
			if(numeroPagina.isPresent()) {
				if( numeroPagina.get() <= 0) throw new DadosInvalidosException("Parâmetros de paginação inválidos");
			}

			if(tamanhoPagina.isPresent()) {
				if( tamanhoPagina.get() <= 0) throw new DadosInvalidosException("Parâmetros de paginação inválidos");
			}

			Integer total = dao.totalEmAberto();
			Integer numPag = numeroPagina.orElseGet(() -> 1);
			Integer tamPag = tamanhoPagina.orElseGet(() -> 10);
			Integer offset = (numPag -1) * tamPag;
			if (offset > total) throw new DadosInvalidosException("A Página selecionada não existe, selecione outra página ou altere o tamanho dessa");

			PedidosPaginados p = new PedidosPaginados(dao.paginaPedidoEmAberto(offset, tamPag), total, numPag, tamPag);
			return ResponseEntity.ok().body(p);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}

	@PostMapping("/pedidos/{id}/atribuir-pedido")
	@Operation(summary = "Iniciar tracking", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> atribuicao(@PathVariable Integer id, @RequestBody DadosGeoDTO dadosGeo) {
		try {

			Pedido pedido = servicePedido.findById(id);

			if (pedido == null || !pedido.getStatusPedido().equals(Status.EM_ABERTO)) {
				throw new DadosInvalidosException("Pedido Indisponivel");
			}

			Integer idPessoaEntregadora = MethodsUtil.getIdPessoa();
			PessoaEntregadora pessoa = servicePessoa.findById(idPessoaEntregadora);
			TrackHistory track = new TrackHistory(dadosGeo, pedido, pessoa);

			serviceTrack.cadastrarTracking(track);
			servicePedido.colocarEmRota(pedido);
			
			return ResponseEntity.ok().build();

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}

	@PatchMapping("/pedidos/{id}/concluir")
	@Operation(summary = "Concluir pedido", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> conclusao(@PathVariable Integer id, @RequestBody DadosGeoDTO dadosGeo) {

		try {
			Pedido pedido = servicePedido.findById(id);
			TrackHistory track = serviceTrack.recuperarUltimoPeloPedidoId(id);

			if (pedido == null || !pedido.getStatusPedido().equals(Status.EM_ROTA)) {
				throw new DadosInvalidosException("Não é possível concluir o pedido");
			}

			Integer idPessoaEntregadora = MethodsUtil.getIdPessoa();
			PessoaEntregadora pessoa = servicePessoa.findById(idPessoaEntregadora);

			if (!track.getPessoaEntregadora().equals(pessoa)) {
				return ResponseEntity.status(401).body(new Message("Não autorizado para concluir este pedido"));
			}

			TrackHistory novaTrack = new TrackHistory(dadosGeo, pedido, pessoa);

			serviceTrack.cadastrarTracking(novaTrack);
			servicePedido.concluir(pedido);

			return ResponseEntity.ok().body(null);
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}

	}

	@PatchMapping("/pedidos/{id}/cancelar")
	@Operation(summary = "Cancelar pedido", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> cancelamento(@PathVariable Integer id, @RequestBody DadosGeoDTO dadosGeo) {

		try {
			Pedido pedido = servicePedido.findById(id);
			TrackHistory track = serviceTrack.recuperarUltimoPeloPedidoId(id);

			if (pedido == null || !pedido.getStatusPedido().equals(Status.EM_ROTA)) {
				throw new DadosInvalidosException("Não é possível cancelar o pedido");
			}

			Integer idPessoaEntregadora = MethodsUtil.getIdPessoa();
			PessoaEntregadora pessoa = servicePessoa.findById(idPessoaEntregadora);

			if (!track.getPessoaEntregadora().equals(pessoa)) {
				return ResponseEntity.status(401).body(new Message("Não autorizado para cancelar este pedido"));
			}

			TrackHistory novaTrack = new TrackHistory(dadosGeo, pedido, pessoa);

			serviceTrack.cadastrarTracking(novaTrack);
			servicePedido.cancelar(pedido);

			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}

}
