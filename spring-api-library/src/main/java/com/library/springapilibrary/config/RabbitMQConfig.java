package com.library.springapilibrary.config;

import com.library.springapilibrary.config.properties.AppProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final AppProperties appProperties;

    public RabbitMQConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(appProperties.getRabbitmq().getExchangeName());
    }

    @Bean
    public Queue purchaseQueue() {
        return new Queue(appProperties.getRabbitmq().getPurchaseQueueName(), true);
    }

    @Bean
    public Binding purchaseBinding(Queue purchaseQueue, TopicExchange exchange) {
        return BindingBuilder.bind(purchaseQueue).to(exchange).with(appProperties.getRabbitmq().getPurchaseRoutingKey());
    }
}
