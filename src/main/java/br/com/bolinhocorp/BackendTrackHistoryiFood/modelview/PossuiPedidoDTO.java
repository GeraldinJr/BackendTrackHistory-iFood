package br.com.bolinhocorp.BackendTrackHistoryiFood.modelview;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.Pedido;

public class PossuiPedidoDTO {
    Boolean possuiPedido;
    Pedido pedido;

    public PossuiPedidoDTO(Boolean possuiPedidio, Pedido pedido) {
        this.possuiPedido = possuiPedidio;
        this.pedido = pedido;
    }

    public PossuiPedidoDTO(Pedido pedido) {
        this.possuiPedido = pedido != null;
        this.pedido = pedido;
    }

    public Boolean getPossuiPedidio() {
        return possuiPedido;
    }

    public void setPossuiPedidio(Boolean possuiPedidio) {
        this.possuiPedido = possuiPedidio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
