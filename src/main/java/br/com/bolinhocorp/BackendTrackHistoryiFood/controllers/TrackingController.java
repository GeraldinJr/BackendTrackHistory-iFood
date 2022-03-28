package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoMaisInstDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;
import br.com.bolinhocorp.BackendTrackHistoryiFood.modelview.TrackingsEnvioMV;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPedidoService;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.ITrackHistoryService;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.MethodsUtil;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;

@RestController
@CrossOrigin("*")
public class TrackingController {

	@Autowired
	private ITrackHistoryService serviceTrack;

	@Autowired
	private IPessoaEntregadora servicePessoa;

	@Autowired
	private IPedidoService servicePedido;

	@PostMapping("/pedidos/{id}/geolocalizacao")
	public ResponseEntity<?> adicionarTracking(@PathVariable Integer id, @RequestBody DadosGeoDTO dadosGeo) {

		try {

			Pedido pedido = servicePedido.findById(id);
			TrackHistory ultimoTrack = serviceTrack.recuperarUltimoPeloPedidoId(id);

			if (pedido == null || pedido.getStatusPedido() != Status.EM_ROTA) {
				throw new DadosInvalidosException("Pedido Indisponivel, pois nao esta em rota");
			}

			Integer idPessoaLogada = MethodsUtil.getIdPessoa();
			Integer idPessoaEntregadora = ultimoTrack.getPessoaEntregadora().getId();
			
			if(idPessoaEntregadora != idPessoaLogada) {
				return ResponseEntity.status(401).body(new Message("Nao autorizado para esse pedido"));
			}

			PessoaEntregadora pessoa = servicePessoa.findById(idPessoaLogada);
			TrackHistory track = new TrackHistory(dadosGeo, pedido, pessoa);

			serviceTrack.cadastrarTracking(track);

			return ResponseEntity.status(201).body(new DadosGeoMaisInstDTO(dadosGeo));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}

	}

	@GetMapping("/pedidos/{id}/trackings")
	public ResponseEntity<?> recuperarTodosOsTrackings(@PathVariable Integer id) {
		try {

			Pedido pedido = servicePedido.findById(id);

			if (pedido == null) {
				throw new DadosInvalidosException("Pedido Indisponivel");
			}
			if(pedido.getStatusPedido()==Status.EM_ABERTO) {
				return ResponseEntity.status(404).body(new Message("Pedido em Aberto"));
			}

			List<DadosGeoMaisInstDTO> lista = serviceTrack.recuperarTodos(id);


			return ResponseEntity.ok().body(new TrackingsEnvioMV(lista, pedido.getStatusPedido()));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}

	}

	@GetMapping("/pedidos/{id}/geolocalizacao")
	public ResponseEntity<?> recuperarUltimaGeolocalizacao(@PathVariable Integer id) {
		try {

			Pedido pedido = servicePedido.findById(id);

			if (pedido == null) {
				throw new DadosInvalidosException("Pedido Indisponivel");
			}
			if(pedido.getStatusPedido()==Status.EM_ABERTO) {
				return ResponseEntity.notFound().build();
			}

			TrackHistory track = serviceTrack.recuperarUltimoPeloPedidoId(id);
			DadosGeoMaisInstDTO geolocalizacao = new DadosGeoMaisInstDTO(track);


			return ResponseEntity.ok().body(geolocalizacao);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}

	}

}
