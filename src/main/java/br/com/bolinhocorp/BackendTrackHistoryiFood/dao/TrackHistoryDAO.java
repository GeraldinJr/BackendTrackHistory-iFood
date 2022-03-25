package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackHistoryDAO extends CrudRepository<TrackHistory, Integer>{

    @Query(value="select * from track_history where pedido_id = ?1 order by instante_registro desc limit 1", nativeQuery = true)
    public TrackHistory recuperarUltimoPeloPedidoId(Integer pedido_id);
}
