package com.trinhvu.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhvu.stock.exception.NotFoundException;
import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.model.StockPrice;
import com.trinhvu.stock.repository.StockPriceRepository;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.viewmodel.StocksPricePostVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AlphaVantageService {
    @Value("${application.config.alpha-vantage-url}")
    private String url;
    @Value("${application.config.api-key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StockPriceRepository stockPriceRepository;

    public List<StocksPricePostVm> fetchStockData(String symbol, Stock stock) {
        // Build the URL
        String alphaVantageUrl = UriComponentsBuilder.fromHttpUrl(url + "/query")
                .queryParam("function", "TIME_SERIES_INTRADAY")
                .queryParam("symbol", symbol)
                .queryParam("interval", "1min")
                .queryParam("month", "2024-10")
                .queryParam("outputsize", "compact")
                .queryParam("apikey", apiKey)
                .toUriString();

        // Set up headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Make the API request and get the response as a String
        String responseJson = restTemplate.exchange(
                alphaVantageUrl,
                GET,
                requestEntity,
                String.class
        ).getBody();

        // Parse the JSON and extract the "Time Series (1min)" data
        return parseStockData(responseJson, stock);
    }

    private List<StocksPricePostVm> parseStockData(String json, Stock stock) {
        List<StocksPricePostVm> stockPrices = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode timeSeriesNode = rootNode.path("Time Series (1min)");
//            String lastUpdate = String.valueOf(rootNode.path("Meta Data").path("3. Last Refreshed"));
            Iterator<String> fieldNames = timeSeriesNode.fieldNames();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String lastRefreshed = rootNode.path("Meta Data").path("3. Last Refreshed").asText();
            LocalDateTime lastUpdateTime = LocalDateTime.parse(lastRefreshed, formatter);
            Optional<StockPrice> existingPrice = stockPriceRepository.findByStockIdAndTimestamp(stock, lastUpdateTime);

            if (existingPrice.isPresent()) {
                return List.of();
            }
            while (fieldNames.hasNext()) {
                String timestamp = fieldNames.next();
                JsonNode priceDataNode = timeSeriesNode.path(timestamp);

                // Convert timestamp string to ZonedDateTime
                LocalDateTime localDateTime = LocalDateTime.parse(timestamp, formatter);
                // Extract the price data for each timestamp
                double openPrice = priceDataNode.path("1. open").asDouble();
                double highPrice = priceDataNode.path("2. high").asDouble();
                double lowPrice = priceDataNode.path("3. low").asDouble();
                double closePrice = priceDataNode.path("4. close").asDouble();
                double volume = priceDataNode.path("5. volume").asDouble();

                // Create a StocksPricePostVm record
                StocksPricePostVm stockPrice = new StocksPricePostVm(
                        openPrice,
                        closePrice,
                        highPrice,
                        lowPrice,
                        volume,
                        localDateTime
                );

                // Add it to the list
                stockPrices.add(stockPrice);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error parsing stock price data: " + e.getMessage(), e);
        }

        return stockPrices;
    }
}
