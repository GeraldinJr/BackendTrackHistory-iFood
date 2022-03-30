package br.com.bolinhocorp.BackendTrackHistoryiFood.util;

import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class PedidosPaginados {
    Integer paginaAtual;
    Integer tamanhoPagina;
    Integer totalPaginas;
    Integer totalPedidos;
    List<Pedido> pedidos;

    public PedidosPaginados () {
        super();
    }

    public PedidosPaginados (List<Pedido> lista, Integer total, Integer paginaAtual, Integer tamanhoPagina) {
        super();
        this.paginaAtual = paginaAtual;
        this.tamanhoPagina = tamanhoPagina;
        this.totalPaginas = Math.max(1, (int) Math.ceil(total*1.0/tamanhoPagina));
        this.totalPedidos = total;
        this.pedidos = lista;
    }

    public Integer getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(Integer paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    public Integer getTamanhoPagina() {
        return tamanhoPagina;
    }

    public void setTamanhoPagina(Integer tamanhoPagina) {
        this.tamanhoPagina = tamanhoPagina;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public Integer getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(Integer totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
