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

    @Query(value="select count(*) from pedidos where status = 'EM_ABERTO'", nativeQuery = true)
    public Integer totalEmAberto();

    @Query(value="select count(*) from pedidos", nativeQuery = true)
    public Integer total();

    @Query(value="select * from pedidos where status = 'EM_ABERTO' offset ?1 limit ?2", nativeQuery = true)
    public List<Pedido> paginaPedidoEmAberto(Integer offset, Integer limit);

    @Query(value="select * from pedidos offset ?1 limit ?2", nativeQuery = true)
    public List<Pedido> paginaPedido(Integer offset, Integer limit);

    @Query(value="select * from pedidos where status = 'EM_ROTA' and ultima_alteracao < now() - interval '30 minute'", nativeQuery = true)
    public List<Pedido> pedidosEsquecidos();
}