package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPedidoService;
import br.com.bolinhocorp.BackendTrackHistoryiFood.services.IPessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Message;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.MethodsUtil;

@RestController
@CrossOrigin("*")
public class PedidoAltController {

	@Autowired
	private IPedidoService servicePedido;
	
	@Autowired
	private IPessoaEntregadora servicePessoa;

	@PostMapping("/pedidos/{id}/atribuir-pedido")
	public ResponseEntity<?> atribuicao(@PathVariable Integer id, @RequestBody DadosGeoDTO d) {
		// Verificar se o pedido existe
		// Verificar se o pedido esta aberto
		// Se nao estiver aberto, ja cancela
		try {

			Pedido pedido = servicePedido.findById(id);

			if (pedido == null || pedido.getStatusPedido() != Status.EM_ABERTO) {
				throw new DadosInvalidosException("Pedido Indisponivel");
			}
			//alterar o status do pedido
			//pegar os dados e fazer insercao no track
			servicePedido.colocarEmRota(id);
			
			
			Integer  idPessoaEntregadora = MethodsUtil.getIdPessoa();
			PessoaEntregadora pessoa = servicePessoa.findById(idPessoaEntregadora);
			
			TrackHistory track = new TrackHistory(d, pedido, pessoa);
			
			System.out.println(track);
			

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage()));
		}

		return null;
	}

}
