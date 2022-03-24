package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.TrackHistoryDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.TrackHistory;

@Component
public class TrackHistoryImple implements ITrackHistoryService{
	
	@Autowired
	private TrackHistoryDAO dao;

	@Override
	public TrackHistory cadastrarTracking(TrackHistory track) {
		// Fazer validacoes
		try {
			
		track.setId(null);
		dao.save(track);
		
		return null;
		}catch(Exception e) {
			throw new DadosInvalidosException(e.getMessage());
		}
	}

	@Override
	public TrackHistory recuperarUltimoPeloPedidoId(Integer id) {
		return dao.recuperarUltimoPeloPedidoId(id);
	}

}
