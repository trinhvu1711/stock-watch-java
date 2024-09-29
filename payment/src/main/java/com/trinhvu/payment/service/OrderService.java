package com.trinhvu.payment.service;

import com.trinhvu.payment.viewmodel.CapturePayment;
import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${application.config.order-url}")
    private String url;
    private final RestTemplate restTemplate;

    public PaymentOrderStatusVm updateOrderStatus(PaymentOrderStatusVm paymentOrderStatusVm){
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<PaymentOrderStatusVm> requestEntity = new HttpEntity<>(paymentOrderStatusVm ,headers);
        ParameterizedTypeReference<PaymentOrderStatusVm> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<PaymentOrderStatusVm> responseEntity = restTemplate.exchange(
                url + "/status",
                PUT,
                requestEntity,
                responseType
        );
        return responseEntity.getBody();
    }

    public Long updateCheckOutStatus(CapturePayment capturePayment){
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<CapturePayment> requestEntity = new HttpEntity<>(capturePayment ,headers);
        ParameterizedTypeReference<Long> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<Long> responseEntity = restTemplate.exchange(
                url + "/checkouts/status",
                PUT,
                requestEntity,
                responseType
        );
        return responseEntity.getBody();
    }
}
