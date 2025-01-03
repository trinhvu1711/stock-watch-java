package com.trinhvu.stock.service;

import com.binance.connector.client.WebSocketStreamClient;
import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.mapper.BinanceStockGetVmMapper;
import com.trinhvu.stock.viewmodel.binanceStock.BinanceStockGetVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BinanceService {
    private final WebSocketStreamClient wsStreamClient;
    private final StockProducer stockProducer;
    private final BinanceStockGetVmMapper mapper;

//    API Key: 8kKm54pIGsIdVhr6is1NatLv2yGZ0xKaZUqf6VJxeEOQr8Gr08cyUai59MZGnQnw
//    Secret Key: V5LfMiXk287XSr7CVMu2JCEjMPwtomvKitbP5VNXByPj35OuV2VIZl6o7ulsI6Po

    public void startFetchStockData(String symbol) throws InterruptedException {
        wsStreamClient.allTickerStream((event) -> {
            System.out.println(event);
            sendStockData(event);
        });
    }

    public void startFetchAllStockData() throws InterruptedException {
        wsStreamClient.allTickerStream(((event) -> {
            System.out.println(event);
            sendStockData(event);

        }));
    }

    private void sendStockData(String event) {
        BinanceStockGetVm binanceStockGetVm = mapper.convertToViewModel(event);
        stockProducer.sendStockBinancePrice(binanceStockGetVm);

//        add stock into db - repository
    }

    public void stop() {
        wsStreamClient.closeAllConnections();
    }

}
