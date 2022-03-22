package br.com.bolinhocorp.BackendTrackHistoryiFood.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="track_history")
public class TrackHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private Integer id;
	
	@Column(name="instante_registro", nullable=false)
	private Timestamp instante;
	
	@Column(name="latitude", nullable=false)
	private String latitude;
	
	@Column(name="longitude", nullable=false)
	private String longitude;
	
	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="pessoa_entregadora_id")
	private PessoaEntregadora pessoaEntregadora;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getInstante() {
		return instante;
	}

	public void setInstante(Timestamp instante) {
		this.instante = instante;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PessoaEntregadora getPessoaEntregadora() {
		return pessoaEntregadora;
	}

	public void setPessoaEntregadora(PessoaEntregadora pessoaEntregadora) {
		this.pessoaEntregadora = pessoaEntregadora;
	}

}