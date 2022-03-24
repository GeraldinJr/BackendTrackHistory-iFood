package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;
import org.springframework.data.repository.query.Param;

public interface TrackHistoryDAO extends CrudRepository<TrackHistory, Integer>{

    @Query("    SELECT "
            + "new br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory("
            + "  track_history.id,"
            + "  track_history.instante_registro,"
            + "  track_history.latitude,"
            + "  track_history.longitude,"
            + "  track_history.pedido_id)"
            + "  track_history.pessoa_entregadora_id)"
            + "  FROM TrackHistory as track_history"
            + "  WHERE track_history.pedido_id = :pedido_id"
            + "  ORDER BY track_history.instante_registro DESC"
            + "  LIMIT 1")
    public TrackHistory recuperarUltimoPeloPedidoId(@Param("pedido_id") Integer pedido_id);
}
