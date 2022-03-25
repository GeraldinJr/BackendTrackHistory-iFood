package br.com.bolinhocorp.BackendTrackHistoryiFood.modelview;

import java.util.List;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoMaisInstDTO;

public class TrackingsEnvioMV {
	
	private List<DadosGeoMaisInstDTO> data;
	
	public TrackingsEnvioMV(List<DadosGeoMaisInstDTO> data) {
		super();
		this.data = data;
	}

	public List<DadosGeoMaisInstDTO> getData() {
		return data;
	}

	public void setData(List<DadosGeoMaisInstDTO> data) {
		this.data = data;
	}

}
