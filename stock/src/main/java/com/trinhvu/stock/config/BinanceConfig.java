package com.trinhvu.stock.config;

import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceConfig {
    @Bean
    public WebSocketStreamClientImpl webSocketStreamClient() {
        return new WebSocketStreamClientImpl();
    }
}
