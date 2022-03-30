package br.com.bolinhocorp.BackendTrackHistoryiFood.modelview;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

public class PossuiPedidoDTO {
    Boolean possuiPedido;
    Pedido pedido;

    public PossuiPedidoDTO(Boolean possuiPedido, Pedido pedido) {
        this.possuiPedido = possuiPedido;
        this.pedido = pedido;
    }

    public PossuiPedidoDTO(Pedido pedido) {
        this.possuiPedido = pedido != null;
        this.pedido = pedido;
    }

    public Boolean getPossuiPedido() {
        return possuiPedido;
    }

    public void setPossuiPedido(Boolean possuiPedido) {
        this.possuiPedido = possuiPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
