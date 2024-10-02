package com.trinhvu.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhvu.stock.viewmodel.StocksPricePostVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<StocksPricePostVm> fetchStockData(String symbol) {
        // Build the URL
        String alphaVantageUrl = UriComponentsBuilder.fromHttpUrl(url + "/query")
                .queryParam("function", "TIME_SERIES_INTRADAY")
                .queryParam("symbol", symbol)
                .queryParam("interval", "1min")
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
        return parseStockData(responseJson);
    }

    private List<StocksPricePostVm> parseStockData(String json) {
        List<StocksPricePostVm> stockPrices = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode timeSeriesNode = rootNode.path("Time Series (1min)");

            Iterator<String> fieldNames = timeSeriesNode.fieldNames();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");

            while (fieldNames.hasNext()) {
                String timestamp = fieldNames.next();
                JsonNode priceDataNode = timeSeriesNode.path(timestamp);

                // Extract the price data for each timestamp
                double openPrice = priceDataNode.path("1. open").asDouble();
                double highPrice = priceDataNode.path("2. high").asDouble();
                double lowPrice = priceDataNode.path("3. low").asDouble();
                double closePrice = priceDataNode.path("4. close").asDouble();
                double volume = priceDataNode.path("5. volume").asDouble();

                // Convert timestamp string to ZonedDateTime
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(timestamp + "Z", formatter);

                // Create a StocksPricePostVm record
                StocksPricePostVm stockPrice = new StocksPricePostVm(
                        openPrice,
                        closePrice,
                        highPrice,
                        lowPrice,
                        volume,
                        zonedDateTime
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
