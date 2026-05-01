package com.library.springapilibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for setting up WebSocket communication.
 *
 * @Configuration: Marks this as a Spring configuration class.
 * @EnableWebSocketMessageBroker: Enables WebSocket message handling, backed by a message broker.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker.
     * @param config The registry for configuring the message broker.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enables a simple in-memory message broker.
        // Defines that messages whose destination starts with "/topic" will be routed to the broker.
        // This is what clients will subscribe to.
        config.enableSimpleBroker("/topic");

        // Defines the prefix for destinations that are mapped to @MessageMapping annotated methods
        // in controller classes. We are not using this for server-to-client push, but it's
        // good practice to define it.
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registers the STOMP endpoints.
     * @param registry The registry for STOMP endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registers the "/ws" endpoint.
        // We now explicitly list the allowed origin for our React development server
        // instead of using the wildcard "*", which is required when credentials
        // (like in SockJS) are allowed.
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS();
    }
}
