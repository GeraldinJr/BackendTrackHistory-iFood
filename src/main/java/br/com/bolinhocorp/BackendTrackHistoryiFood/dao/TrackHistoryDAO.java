package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;

@Repository
public interface TrackHistoryDAO extends CrudRepository<TrackHistory, Integer>{

    @Query(value="select * from track_history where pedido_id = ?1 order by instante_registro desc limit 1", nativeQuery = true)
    public TrackHistory recuperarUltimoPeloPedidoId(Integer pedido_id);

    @Query(value="select * from track_history where pessoa_entregadora_id = ?1 order by instante_registro desc limit 1", nativeQuery = true)
    public TrackHistory recuperarUltimoPelaPessoaId(Integer pessoa_id);

    @Query(value= "select * from track_history where pedido_id = ?1 offset ?2 limit ?3", nativeQuery = true)
    public List<TrackHistory> findByPedido_id(Integer id, Integer offset, Integer limit);

    @Query(value= "select count(*) from track_history where pedido_id = ?1", nativeQuery = true)
    public Integer total(Integer id);
}
