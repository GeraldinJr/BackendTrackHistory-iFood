package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

public interface PedidoDAO extends CrudRepository<Pedido, Integer> {

}