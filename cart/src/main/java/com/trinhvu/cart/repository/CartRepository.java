package com.trinhvu.cart.repository;

import com.trinhvu.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCustomerId(String id);

    List<Cart> findByCustomerIdAndOrderIdIsNull(String customerId);
}
