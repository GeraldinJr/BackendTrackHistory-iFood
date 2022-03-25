package br.com.bolinhocorp.BackendTrackHistoryiFood.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.PedidoDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

@RestController
public class PedidosController {
	
	@Autowired
	private PedidoDAO dao;
	
	@GetMapping("/pedidos")
	public List<Pedido> recuperarTodos(){
		return (List<Pedido>)dao.findAll();
	}
}