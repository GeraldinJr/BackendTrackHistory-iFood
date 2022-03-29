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

    public PedidosPaginados (List<Pedido> lista, Integer paginaAtual, Integer tamanhoPagina) {
        super();
        if( paginaAtual <= 0 || tamanhoPagina <=0) throw new DadosInvalidosException("Parâmetros de paginação inválidos");
        this.paginaAtual = paginaAtual;
        this.tamanhoPagina = tamanhoPagina;
        this.totalPaginas = Math.max(1, (int) Math.ceil(lista.size()*1.0/tamanhoPagina));
        this.totalPedidos = lista.size();

        int inicio = (paginaAtual-1)*tamanhoPagina;
        if (inicio > lista.size()) throw new DadosInvalidosException("A Página selecionada não existe, selecione outra página ou altere o tamanho dessa");
        int fim = Math.min(inicio + tamanhoPagina, lista.size());
        this.pedidos = lista.subList(inicio, fim);
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
