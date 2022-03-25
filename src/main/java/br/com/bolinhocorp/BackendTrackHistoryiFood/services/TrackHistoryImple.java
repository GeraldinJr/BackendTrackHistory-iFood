package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.TrackHistoryDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoMaisInstDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;

@Component
public class TrackHistoryImple implements ITrackHistoryService {

	@Autowired
	private TrackHistoryDAO dao;

	@Override
	public TrackHistory cadastrarTracking(TrackHistory track) {
		// Fazer validacoes
		try {

			track.setId(null);
			dao.save(track);

			return null;
		} catch (Exception e) {
			throw new DadosInvalidosException(e.getMessage());
		}
	}

	@Override
	public ArrayList<DadosGeoMaisInstDTO> recuperarTodos(Integer id) {
		try {
			
			List<TrackHistory> res = (List<TrackHistory>)dao.findAll();
			
			ArrayList<DadosGeoMaisInstDTO> lista = new ArrayList<DadosGeoMaisInstDTO>();
			
			for(TrackHistory tr: res) {
				lista.add(new DadosGeoMaisInstDTO(tr));
			}
			
			return lista;

		} catch (Exception e) {
			throw new DadosInvalidosException(e.getMessage());
		}
	}

}
