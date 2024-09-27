package com.trinhvu.order.repository;

import com.trinhvu.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN o.orderItems oi "
            +  "WHERE LOWER(o.email) LIKE %:email% "
            + "AND LOWER(oi.stockName) LIKE %:stockName% "
            + "AND o.createdOn BETWEEN :createdFrom AND :createdTo "
            + "ORDER BY o.createdOn DESC")
    Page<Order> findOrderByWithMulCriteria(
            @Param("email") String email,
            @Param("stockName") String stockName,
            @Param("createdFrom") ZonedDateTime createdFrom,
            @Param("createdTo") ZonedDateTime createdTo,
            Pageable pageable);
}
