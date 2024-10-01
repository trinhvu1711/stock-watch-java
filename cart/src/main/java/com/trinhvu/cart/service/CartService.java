package com.trinhvu.cart.service;

import com.trinhvu.cart.repository.CartRepository;
import com.trinhvu.cart.viewmodel.CartGetDetailVm;
import com.trinhvu.cart.viewmodel.CartItemVm;
import com.trinhvu.cart.viewmodel.CartListVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public CartGetDetailVm createCart(CartItemVm cartItemVm) {
        return null;
    }

    public CartGetDetailVm getCartByCustomerId(Long id) {
        return null;
    }

    public CartListVm getCartWithFilter(int pageNo, int pageSize) {
        return null;
    }

    public void updateCart(CartItemVm cartItemVm) {

    }

    public void removeCartItemByStockId(Long id) {

    }

    public void removeCartItemByStockIdList(List<Long> stockIds) {

    }

    public Long countNumberItemInCart(String name) {
        return null;
    }

    public Long getLastCart(String name) {
        return null;
    }
}
