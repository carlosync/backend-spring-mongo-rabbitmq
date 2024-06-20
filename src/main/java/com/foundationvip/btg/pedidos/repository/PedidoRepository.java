package com.foundationvip.btg.pedidos.repository;

import com.foundationvip.btg.pedidos.domain.PedidoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PedidoRepository extends MongoRepository<PedidoEntity, Long> {
}
