package com.foundationvip.btg.pedidos.listener.dto;

import java.util.List;

public record PedidoCreatedEvent(Long codigoPedido, Long codigoCliente, List<PedidoItemEvent> itens) {
}
