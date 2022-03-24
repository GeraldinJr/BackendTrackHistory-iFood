package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;

public interface TrackHistoryDAO extends CrudRepository<TrackHistory, Integer>{

}
