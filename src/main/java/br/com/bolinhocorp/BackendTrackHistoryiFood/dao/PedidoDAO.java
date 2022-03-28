package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

import java.util.List;

public interface PedidoDAO extends CrudRepository<Pedido, Integer> {

    @Query(value="select * from pedidos where status = ?1", nativeQuery = true)
    public List<Pedido> recuperarPedidosPorStatus(String status);
}