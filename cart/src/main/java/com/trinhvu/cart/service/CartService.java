package com.trinhvu.cart.service;

import com.trinhvu.cart.exception.BadRequestException;
import com.trinhvu.cart.repository.CartRepository;
import com.trinhvu.cart.utils.Constants;
import com.trinhvu.cart.viewmodel.CartDetailVm;
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

    public CartGetDetailVm createCart(List<CartItemVm> cartItemVm) {
        List<Long> stockIds = cartItemVm.stream().map(CartItemVm::stockId).toList();
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

    public void removeCartItemByStockIdList(List<Long> stockIds, String customerId) {
        CartGetDetailVm currentCart = getLastCart(customerId);
        stockIds.forEach(stockId -> validateCart(currentCart, stockId));
        cartRepository.deleteByCartIdAndStockIdIn(currentCart.id(), stockIds);
    }

    public Long countNumberItemInCart(String name) {
        return null;
    }

    public CartGetDetailVm getLastCart(String name) {
        return null;
    }

    private void validateCart(CartGetDetailVm cartGetDetailVm, Long stockId) {
        if(cartGetDetailVm.cartItemDetailVms().isEmpty()){
            throw new BadRequestException(Constants.ErrorCode.NOT_EXISTING_ITEM_IN_CART);
        }

        List<CartDetailVm> cartDetailVms = cartGetDetailVm.cartItemDetailVms();
        boolean itemExit = cartDetailVms.stream().anyMatch(cartDetailVm -> cartDetailVm.stockId().equals(stockId));
        if(!itemExit){
            throw new BadRequestException(Constants.ErrorCode.NOT_EXISTING_STOCK_IN_CART, stockId);
        }
    }
}
