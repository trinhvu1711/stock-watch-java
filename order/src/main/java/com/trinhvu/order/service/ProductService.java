package com.trinhvu.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Value("${application.config.product-url}")
    private String url;
    private final RestTemplate restTemplate;
}
