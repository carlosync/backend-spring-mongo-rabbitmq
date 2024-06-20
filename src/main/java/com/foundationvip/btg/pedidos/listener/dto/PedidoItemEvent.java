package com.foundationvip.btg.pedidos.listener.dto;

import java.math.BigDecimal;

public record PedidoItemEvent(String produto, Integer quantidade, BigDecimal preco) {
}
