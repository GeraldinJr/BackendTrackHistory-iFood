package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

public interface PedidoDAO extends CrudRepository<Pedido, Integer> {
	
//	@Modifying
//	@Query("update "
//			+ "new br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido() p "
//			+ "set p.status = 1 where p.id = :id"
//			)
//	public void colocarPedidoEmRota(Integer id);

}