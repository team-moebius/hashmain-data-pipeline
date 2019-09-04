package com.moebius.tracker.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import org.springframework.web.reactive.socket.client.WebSocketClient
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Configuration
class WebSocketConfiguration {
    @Bean
    fun webSocketClient(): WebSocketClient = ReactorNettyWebSocketClient()

    @Bean
    fun openedSessions(): ConcurrentMap<String, WebSocketSession> = ConcurrentHashMap()
}