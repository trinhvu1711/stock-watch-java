package com.trinhvu.order.repository;

import com.trinhvu.order.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, String> {
}
