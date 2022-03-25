package br.com.bolinhocorp.BackendTrackHistoryiFood.models;

import br.com.bolinhocorp.BackendTrackHistoryiFood.util.Status;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;


	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private Status statusPedido;
	
	@Column(name="ultima_alteracao", nullable=false)
	private Timestamp ultimaAlteracao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Status getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(Status statusPedido) {
		this.statusPedido = statusPedido;
	}

	public Timestamp getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Timestamp ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

}
