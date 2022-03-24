package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

public interface IPedidoService {
	public boolean existsById(int id);
	public Pedido findById(int id);
	public void colocarEmRota(int id);

}
