package br.com.bolinhocorp.BackendTrackHistoryiFood.modelview;

import java.util.List;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.DadosGeoMaisInstDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;

public class TrackingsEnvioMV {
	
	private List<DadosGeoMaisInstDTO> data;
	private Status status_pedido;
	
	public TrackingsEnvioMV(List<DadosGeoMaisInstDTO> data, Status statusPedido) {
		super();
		this.data = data;
		this.status_pedido = statusPedido;
	}

	public Status getStatus_pedido() {
		return status_pedido;
	}

	public void setStatus_pedido(Status status_pedido) {
		this.status_pedido = status_pedido;
	}

	public List<DadosGeoMaisInstDTO> getData() {
		return data;
	}

	public void setData(List<DadosGeoMaisInstDTO> data) {
		this.data = data;
	}

}
