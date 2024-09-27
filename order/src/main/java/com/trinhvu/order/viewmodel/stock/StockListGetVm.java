package com.trinhvu.order.viewmodel.stock;

import java.util.List;

public record StockListGetVm(
        List<StockListVm> stocks,
        int pageNo,
        int pageSize,
        long totalElement,
        int totalPage,
        boolean isLast
) {

}
