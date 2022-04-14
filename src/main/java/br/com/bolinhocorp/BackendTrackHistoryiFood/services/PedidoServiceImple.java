package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.PedidoDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.MethodsUtil;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;

@Component
@EnableScheduling
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
		atualizarUltimaAlteracao(pedido);
		dao.save(pedido);

	}

	@Override
	public void concluir(Pedido pedido) {
		pedido.setStatusPedido(Status.CONCLUIDO);
		atualizarUltimaAlteracao(pedido);
		dao.save(pedido);
	}

	@Override
	public void cancelar(Pedido pedido) {
		pedido.setStatusPedido(Status.EM_ABERTO);
		atualizarUltimaAlteracao(pedido);
		dao.save(pedido);
	}

	@Override
	public void atualizarUltimaAlteracao(Pedido pedido) {
		pedido.setUltimaAlteracao(new Timestamp(System.currentTimeMillis()));
	}

	@Override
	@Scheduled(fixedDelay = 35 * MethodsUtil.MINUTOS)
	public void cancelarPedidosEsquecidos() {
		List<Pedido> lista = dao.pedidosEsquecidos();
		for(Pedido p: lista) {
			p.setStatusPedido(Status.CANCELADO);
			atualizarUltimaAlteracao(p);
			dao.save(p);
		}
	}
}
