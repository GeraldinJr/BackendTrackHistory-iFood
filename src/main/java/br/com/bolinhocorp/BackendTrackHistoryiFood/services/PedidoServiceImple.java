package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

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
	public void colocarEmRota(int id) {
		Pedido p = dao.findById(id).orElse(null);
		p.setStatusPedido(2);
		dao.save(p);
	}

}
