package com.foundationvip.btg.pedidos.service;

import com.foundationvip.btg.pedidos.controller.dto.PedidoResponse;
import com.foundationvip.btg.pedidos.domain.PedidoEntity;
import com.foundationvip.btg.pedidos.domain.PedidoItem;
import com.foundationvip.btg.pedidos.listener.dto.PedidoCreatedEvent;
import com.foundationvip.btg.pedidos.repository.PedidoRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MongoTemplate mongoTemplate;

    public PedidoService(PedidoRepository pedidoRepository, MongoTemplate mongoTemplate) {
        this.pedidoRepository = pedidoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(PedidoCreatedEvent event){
        var entity = new PedidoEntity();
        entity.setPedidoId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getPedidoItems(event));
        entity.setTotal(getTotal(event));

        pedidoRepository.save(entity);
    }

    public Page<PedidoResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest){
        var pedidos = pedidoRepository.findAllByCustomerId(customerId, pageRequest);
        return pedidos.map(PedidoResponse::fromEntity);
    }

    public BigDecimal findTotalOnPedidosByCustomerId(Long customerId){
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is("customerId")),
                group().sum("total").as("total")
        );
        var response = mongoTemplate.aggregate(aggregations, "tb_pedidos", Document.class);
        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
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
