package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.TrackHistoryDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoMaisInstDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.NaoAutorizadoAuthException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.NaoEncontradoException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.PedidoIndisponivelException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPedidoService;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.ITrackHistoryService;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.MethodsUtil;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.TrackingsPaginadas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin("*")
public class TrackingController {

	@Autowired
	private ITrackHistoryService serviceTrack;

	@Autowired
	private IPessoaEntregadora servicePessoa;

	@Autowired
	private IPedidoService servicePedido;

	@Autowired
	private TrackHistoryDAO dao;

	@PostMapping("/pedidos/{id}/geolocalizacao")
	@Operation(summary = "Enviar geolocalização", description = "Este endpoint recebe a última geolocalização do pedido, e a registra no banco de dados.", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> adicionarTracking(@PathVariable Integer id, @RequestBody DadosGeoDTO dadosGeo) {

//		try {

			Pedido pedido = servicePedido.findById(id);
			TrackHistory ultimoTrack = serviceTrack.recuperarUltimoPeloPedidoId(id);

			if(pedido == null) {
				throw new NaoEncontradoException("Pedido não encontrado.");
			}

			if (!pedido.getStatusPedido().equals(Status.EM_ROTA)) {
				throw new PedidoIndisponivelException("Pedido Indisponivel");
			}

			Integer idPessoaLogada = MethodsUtil.getIdPessoa();
			Integer idPessoaEntregadora = ultimoTrack.getPessoaEntregadora().getId();
			
			if(idPessoaEntregadora != idPessoaLogada) {
				throw new NaoAutorizadoAuthException("Nao autorizado para esse pedido");
			}

			PessoaEntregadora pessoa = servicePessoa.findById(idPessoaLogada);
			TrackHistory track = new TrackHistory(dadosGeo, pedido, pessoa);

			serviceTrack.cadastrarTracking(track);
			servicePedido.atualizarUltimaAlteracao(pedido);

			return ResponseEntity.status(201).body(new DadosGeoMaisInstDTO(dadosGeo));

//		} catch (Exception e) {
////			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
//			throw new ErroInternoException(e.getMessage());
//		}

	}

	@GetMapping("/pedidos/{id}/trackings")
	@Operation(summary = "Carregar todas as geolocalizações do pedido", description = "Este endpoint retorna o histórico de todas as geolocalizações do pedido.", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> recuperarTodosOsTrackings(@PathVariable Integer id, @RequestParam Optional<Integer> numeroPagina, @RequestParam Optional<Integer> tamanhoPagina) {
//		try {

			if(numeroPagina.isPresent()) {
				if( numeroPagina.get() <= 0) throw new DadosInvalidosException("Parâmetros de paginação inválidos");
			}

			if(tamanhoPagina.isPresent()) {
				if( tamanhoPagina.get() <= 0) throw new DadosInvalidosException("Parâmetros de paginação inválidos");
			}

			Pedido pedido = servicePedido.findById(id);

			if (pedido == null) {
				throw new NaoEncontradoException("Pedido Indisponivel");
			}
			if(pedido.getStatusPedido()==Status.EM_ABERTO) {
				throw new NaoEncontradoException("Pedido em Aberto");
			}

			Integer total = dao.total(id);
			Integer numPag = numeroPagina.orElseGet(() -> 1);
			Integer tamPag = tamanhoPagina.orElseGet(() -> 10);
			tamPag = tamPag > 100? 100 : tamPag;
			Integer offset = (numPag -1) * tamPag;

			List<DadosGeoMaisInstDTO> lista = serviceTrack.recuperarTodos(id, offset, tamPag);


			return ResponseEntity.ok().body(new TrackingsPaginadas(lista, total, pedido.getStatusPedido(), numPag, tamPag));

//		} catch (Exception e) {
////			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
//			throw new ErroInternoException(e.getMessage());
//		}

	}

	@GetMapping("/pedidos/{id}/geolocalizacao")
	@Operation(summary = "Carregar última geolocalização do pedido", description = "Este endpoint retorna a última geolocalização registrada do pedido.", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> recuperarUltimaGeolocalizacao(@PathVariable Integer id) {
//		try {

			Pedido pedido = servicePedido.findById(id);

			if (pedido == null) {
				throw new NaoEncontradoException("Pedido Inexistente");
			}
			if(pedido.getStatusPedido()==Status.EM_ABERTO) {
				throw new NaoEncontradoException("Pedido em Aberto");
			}

			TrackHistory track = serviceTrack.recuperarUltimoPeloPedidoId(id);
			DadosGeoMaisInstDTO geolocalizacao = new DadosGeoMaisInstDTO(track);


			return ResponseEntity.ok().body(geolocalizacao);

//		} catch (Exception e) {
//			throw new ErroInternoException(e.getMessage());
//		}

	}

}
