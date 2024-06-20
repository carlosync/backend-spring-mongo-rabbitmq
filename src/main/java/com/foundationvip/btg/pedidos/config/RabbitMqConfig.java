package com.foundationvip.btg.pedidos.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String PEDIDO_CREATED_QUEUE = "btg-pactual-pedido_created";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarable pedidoCreatedQueue() {
        return new Queue(PEDIDO_CREATED_QUEUE);
    }
}
