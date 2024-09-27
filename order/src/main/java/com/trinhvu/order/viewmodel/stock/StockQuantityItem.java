package com.trinhvu.order.viewmodel.stock;

import lombok.Builder;

@Builder
public record StockQuantityItem(Long stockId, int quantity) {
}
