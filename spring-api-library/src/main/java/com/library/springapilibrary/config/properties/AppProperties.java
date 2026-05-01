package com.library.springapilibrary.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Cors cors = new Cors();
    private final Jwt jwt = new Jwt();
    private final RabbitMQ rabbitmq = new RabbitMQ();

    public Cors getCors() {
        return cors;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public RabbitMQ getRabbitmq() {
        return rabbitmq;
    }

    public static class Cors {
        private String allowedOrigins;

        public String getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(String allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }
    }

    public static class Jwt {
        private String secret;
        private long expirationMs;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public long getExpirationMs() {
            return expirationMs;
        }

        public void setExpirationMs(long expirationMs) {
            this.expirationMs = expirationMs;
        }
    }

    public static class RabbitMQ {
        private String exchangeName;
        private String purchaseQueueName;
        private String purchaseRoutingKey;

        public String getExchangeName() {
            return exchangeName;
        }

        public void setExchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
        }

        public String getPurchaseQueueName() {
            return purchaseQueueName;
        }

        public void setPurchaseQueueName(String purchaseQueueName) {
            this.purchaseQueueName = purchaseQueueName;
        }

        public String getPurchaseRoutingKey() {
            return purchaseRoutingKey;
        }

        public void setPurchaseRoutingKey(String purchaseRoutingKey) {
            this.purchaseRoutingKey = purchaseRoutingKey;
        }
    }
}
