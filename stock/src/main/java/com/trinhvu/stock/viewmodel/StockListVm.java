package com.trinhvu.stock.viewmodel;

import java.util.List;

public record StockListVm(
        List<StocksGetVm> stocks,
        int pageNo,
        int pageSize,
        long totalElement,
        int totalPage,
        boolean isLast
) {
}
