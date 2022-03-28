package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;
import org.springframework.data.repository.query.Param;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public interface PedidoDAO extends CrudRepository<Pedido, Integer> {

    @Query(value="select * from pedidos where status = 'EM_ABERTO'", nativeQuery = true)
    public List<Pedido> recuperarPedidosPorStatus();
}