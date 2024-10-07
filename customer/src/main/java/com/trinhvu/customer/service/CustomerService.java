package com.trinhvu.customer.service;

import com.trinhvu.customer.viewmodel.CustomerAdminVm;
import com.trinhvu.customer.viewmodel.CustomerListVm;
import com.trinhvu.customer.viewmodel.CustomerPutVm;
import com.trinhvu.customer.viewmodel.CustomerVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {
    public CustomerListVm getCustomers(int pageNo) {
        return null;
    }

    public CustomerAdminVm getCustomerByEmail(String email) {
        return null;
    }

    public CustomerVm getCustomerProfile() {
        return null;
    }

    public CustomerVm createGuestUser() {
        return null;
    }

    public Void updateProfile(CustomerPutVm customerVm) {
        return null;
    }
}
