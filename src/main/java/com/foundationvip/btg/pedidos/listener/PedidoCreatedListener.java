package com.foundationvip.btg.pedidos.listener;

import com.foundationvip.btg.pedidos.listener.dto.PedidoCreatedEvent;
import com.foundationvip.btg.pedidos.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


import static com.foundationvip.btg.pedidos.config.RabbitMqConfig.PEDIDO_CREATED_QUEUE;

@Component
public class PedidoCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(PedidoCreatedListener.class);

    private final PedidoService pedidoService;

    public PedidoCreatedListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = PEDIDO_CREATED_QUEUE)
    public void listen(Message<PedidoCreatedEvent> message){
        logger.info("Message consumed: {}", message);

        pedidoService.save(message.getPayload());
    }
}
