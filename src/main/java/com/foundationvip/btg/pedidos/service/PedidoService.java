package com.foundationvip.btg.pedidos.service;

import com.foundationvip.btg.pedidos.domain.PedidoEntity;
import com.foundationvip.btg.pedidos.domain.PedidoItem;
import com.foundationvip.btg.pedidos.listener.dto.PedidoCreatedEvent;
import com.foundationvip.btg.pedidos.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void save(PedidoCreatedEvent event){
        var entity = new PedidoEntity();
        entity.setPedidoId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getPedidoItems(event));
        entity.setTotal(getTotal(event));

        pedidoRepository.save(entity);
    }

    private BigDecimal getTotal(PedidoCreatedEvent event) {
        return event.itens().stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private static List<PedidoItem> getPedidoItems(PedidoCreatedEvent event) {
        return event.itens().stream()
                .map(i -> new PedidoItem(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }
}
