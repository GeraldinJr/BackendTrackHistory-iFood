package br.com.bolinhocorp.BackendTrackHistoryiFood.dto;

import java.sql.Timestamp;

public class DadosGeoMaisInstDTO {
	private Double latitude;
	private Double longitude;
	private Timestamp instante;
	
	public DadosGeoMaisInstDTO(DadosGeoDTO dadosGeo) {
		super();
		this.latitude = dadosGeo.getLatitude();
		this.longitude = dadosGeo.getLongitude();
		this.instante = new Timestamp(System.currentTimeMillis());
	}
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Timestamp getInstante() {
		return instante;
	}
	public void setInstante(Timestamp instante) {
		this.instante = instante;
	}
}
