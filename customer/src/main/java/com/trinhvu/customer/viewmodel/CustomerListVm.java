package com.trinhvu.customer.viewmodel;

import java.util.List;

public record CustomerListVm(int totalUser, List<CustomerAdminVm> customerVms, int totalPage) {
}
