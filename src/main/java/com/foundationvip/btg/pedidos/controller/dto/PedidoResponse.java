package com.foundationvip.btg.pedidos.controller.dto;

import com.foundationvip.btg.pedidos.domain.PedidoEntity;

import java.math.BigDecimal;

public record PedidoResponse(Long pedidoId,
                             Long customerId,
                             BigDecimal total) {

    public static PedidoResponse fromEntity(PedidoEntity entity){
        return new PedidoResponse(entity.getPedidoId(), entity.getCustomerId(), entity.getTotal());
    }
}
