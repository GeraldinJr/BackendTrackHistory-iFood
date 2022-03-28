package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.TrackHistoryDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.PedidoDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

@Component
public class PedidoServiceImple implements IPedidoService{
	
	@Autowired
	private PedidoDAO dao;

	@Override
	public boolean existsById(int id) {
		return dao.existsById(id);
	}

	@Override
	public Pedido findById(int id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public void colocarEmRota(Pedido pedido) {
		pedido.setStatusPedido(Status.EM_ROTA);
		dao.save(pedido);

	}

	@Override
	public void concluir(Pedido pedido) {
		pedido.setStatusPedido(Status.CONCLUIDO);
		dao.save(pedido);
	}

	@Override
	public void cancelar(Pedido pedido) {
		pedido.setStatusPedido(Status.CANCELADO);
		dao.save(pedido);
	}
}
