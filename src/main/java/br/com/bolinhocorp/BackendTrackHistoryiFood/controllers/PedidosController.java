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
	public ResponseEntity<?> recuperarTodos(@RequestParam Optional<Integer> numeroPagina, @RequestParam Optional<Integer> tamanhoPagina){
		try {
			List<Pedido> lista = (List<Pedido>) dao.findAll();
			PedidosPaginados p = new PedidosPaginados(lista, numeroPagina.orElseGet(() -> 1), tamanhoPagina.orElseGet(() -> 10));
			return ResponseEntity.ok().body(p);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}

	@GetMapping("/pedidos/em-aberto")
	public ResponseEntity<?> recuperarPedidosEmAberto(@RequestParam Optional<Integer> numeroPagina, @RequestParam Optional<Integer> tamanhoPagina) {

		try {
			List<Pedido> lista = (List<Pedido>) dao.recuperarPedidosPorStatus("EM_ABERTO");
			PedidosPaginados p = new PedidosPaginados(lista, numeroPagina.orElseGet(() -> 1), tamanhoPagina.orElseGet(() -> 10));
			return ResponseEntity.ok().body(p);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}
	}

	@PostMapping("/pedidos/{id}/atribuir-pedido")
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
