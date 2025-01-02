package com.ELOUSTA.ELOUSTA.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket configuration class for enabling and configuring WebSocket message broker.
 * This configuration enables real-time, two-way communication between clients and the server.
 */
@Configuration
@EnableWebSocketMessageBroker  // This annotation enables WebSocket support and message brokering.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker for the WebSocket connection.
     * The broker is responsible for handling messages sent to specific destinations (channels).
     *
     * @param config - the registry used to configure the message broker.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple message broker that will route messages to destinations prefixed with /subscribe
        config.enableSimpleBroker("/subscribe");  // "/subscribe" is the topic to which clients can subscribe.
    }

    /**
     * Registers the STOMP endpoints for WebSocket communication.
     * These endpoints will be used by clients to establish WebSocket connections.
     *
     * @param registry - the registry used to register STOMP endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the WebSocket endpoint that clients will use to connect.
        // When clients connect, they will use "/elousta-websocket" as the connection URL.
        registry.addEndpoint("/elousta-websocket");
    }
}
