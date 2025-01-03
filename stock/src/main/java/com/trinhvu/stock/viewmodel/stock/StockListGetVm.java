package com.trinhvu.stock.viewmodel.stock;

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
