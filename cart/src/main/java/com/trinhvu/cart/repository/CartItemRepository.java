package com.trinhvu.cart.repository;

import com.trinhvu.cart.model.Cart;
import com.trinhvu.cart.model.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findAllByCart(Cart cart);

    @Transactional
    void deleteByCartIdAndStockIdIn(Long cartId, List<Long> stockIds);

    @Query("SELECT SUM(ci.quantity) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Long countItemInCart(@Param("cartId") Long cartId);

    @Transactional
    void deleteByCartIdAndStockId(Long cartId, Long stockId);
}
