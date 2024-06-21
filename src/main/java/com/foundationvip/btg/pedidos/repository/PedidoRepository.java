package com.foundationvip.btg.pedidos.repository;

import com.foundationvip.btg.pedidos.controller.dto.PedidoResponse;
import com.foundationvip.btg.pedidos.domain.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PedidoRepository extends MongoRepository<PedidoEntity, Long> {
    Page<PedidoEntity> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}
