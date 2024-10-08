package com.trinhvu.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinhvu.stock.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private String getKeyFrom(String symbol,
                              String name,
                              Pageable pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();

        Sort.Order order = sort.getOrderFor("id");
        String sortDirection = "unsorted"; // Default value in case of no sort
        if (order != null) {
            sortDirection = order.getDirection() == Sort.Direction.ASC ? "asc" : "desc";
        } else {
            // Log or handle case where there's no sorting by 'id'
            System.out.println("No sorting by 'id', setting direction as unsorted");
        }

        String key = String.format("all_stocks:%s:%s:%d:%d:%s", symbol, name, pageNumber, pageSize, sortDirection);
//        System.out.println(key);
        return key;
        /*
        {
            "all_products:1:10:asc": "list of products object"
        }
        * */
    }

    public Page<Stock> getAllStocks(String symbol,
                                    String name,
                                    Pageable pageRequest) throws JsonProcessingException {

        String key = this.getKeyFrom(symbol, name, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        List<Stock> stocks =
                json != null ?
                        redisObjectMapper.readValue(json, new TypeReference<List<Stock>>() {})
                        : null;
        if (stocks != null) {
            // Wrap the List<Stock> in a Page object
            return new PageImpl<>(stocks, pageRequest, stocks.size());
        }
        return Page.empty(pageRequest);
    }

    public void clear(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    //save to Redis
    public void saveAllProducts(List<Stock> stocks,
                                String symbol,
                                String name,
                                Pageable pageRequest) throws JsonProcessingException {
        String key = this.getKeyFrom(symbol, name, pageRequest);
        String json = redisObjectMapper.writeValueAsString(stocks);
        redisTemplate.opsForValue().set(key, json);
    }


}
