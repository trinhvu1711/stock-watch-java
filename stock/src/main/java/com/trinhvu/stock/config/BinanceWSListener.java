//package com.trinhvu.stock.config;
//
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.socket.client.WebSocketClient;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@Service
//public class BinanceWSListener {
//    private final String BASE_URL = "wss://stream.binance.com:9443/ws/";
//    private WebSocketClient webSocketClient;
//
//    @Value("${binance.api.key}")
//    private String apiKey;
//
//    @Value("${binance.api.secret}")
//    private String secretKey;
//
//    public void startWebSocket() {
//        String streamUrl = BASE_URL + "btcusdt@trade";  // Để theo dõi giao dịch BTC/USDT
//
//        try {
//            this.webSocketClient = new WebSocketClient(new URI(streamUrl)) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    System.out.println("WebSocket Opened");
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    System.out.println("Message received: " + message);
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    System.out.println("Closed with exit code " + code + " additional info: " + reason);
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    System.err.println("Error occurred: " + ex.getMessage());
//                }
//            };
//
//            // Mở WebSocket connection
//            webSocketClient.connect();
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void stopWebSocket() {
//        if (webSocketClient != null && webSocketClient.isOpen()) {
//            webSocketClient.close();
//        }
//    }
//}
