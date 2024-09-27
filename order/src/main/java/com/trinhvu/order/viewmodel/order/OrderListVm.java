package com.trinhvu.order.viewmodel.order;

import java.util.List;

public record OrderListVm(
        List<OrderBriefVm> orderlist,
        long totalElements,
        int totalPages
) {
}
