package com.trinhvu.stock.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trinhvu.stock.exception.BadRequestException;
import com.trinhvu.stock.viewmodel.binanceStock.BinanceStockGetVm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class BinanceStockGetVmMapper {
    private final ObjectMapper objectMapper;

    public BinanceStockGetVmMapper() {
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }

    public BinanceStockGetVm convertToViewModel(String websocketData) {
        try {
            JsonNode node = objectMapper.readTree(websocketData);

            return new BinanceStockGetVm(
                    node.get("e").asText(),
                    Instant.ofEpochMilli(node.get("E").asLong()),
                    node.get("s").asText(),
                    getBigDecimal(node, "p"),
                    getBigDecimal(node, "P"),
                    getBigDecimal(node, "w"),
                    getBigDecimal(node, "c"),
                    getBigDecimal(node, "Q"),
                    getBigDecimal(node, "b"),
                    getBigDecimal(node, "B"),
                    getBigDecimal(node, "a"),
                    getBigDecimal(node, "A"),
                    getBigDecimal(node, "o"),
                    getBigDecimal(node, "h"),
                    getBigDecimal(node, "l"),
                    getBigDecimal(node, "v"),
                    getBigDecimal(node, "q")
            );
        } catch (Exception e) {
            log.error("Error converting websocket data: {}", e.getMessage());
            throw new BadRequestException("Failed to convert websocket data", e);
        }
    }

    public List<BinanceStockGetVm> convertToViewModelList(String websocketData) {
        try {
            JsonNode rootNode = objectMapper.readTree(websocketData);
            if (!rootNode.isArray()) {
                throw new BadRequestException("Expected an array of stock data");
            }

            List<BinanceStockGetVm> result = new ArrayList<>();
            for (JsonNode node : rootNode) {
                result.add(convertToViewModel(node.toString()));
            }

            return result;
        } catch (Exception e) {
            log.error("Error converting websocket data to list: {}", e.getMessage());
            throw new BadRequestException("Failed to convert websocket data to list", e);
        }
    }

    private BigDecimal getBigDecimal(JsonNode node, String fieldName) {
        try {
            JsonNode valueNode = node.get(fieldName);
            return valueNode != null && !valueNode.isNull()
                    ? new BigDecimal(valueNode.asText())
                    : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            log.warn("Failed to parse BigDecimal for field {}", fieldName);
            return BigDecimal.ZERO;
        }
    }
}
