package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import java.util.ArrayList;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoMaisInstDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;

public interface ITrackHistoryService {
	public TrackHistory cadastrarTracking(TrackHistory track);
	public ArrayList<DadosGeoMaisInstDTO> recuperarTodos(Integer id);
}
