package com.trinhvu.cart.service;

import com.trinhvu.cart.exception.BadRequestException;
import com.trinhvu.cart.exception.NotFoundException;
import com.trinhvu.cart.model.Cart;
import com.trinhvu.cart.model.CartItem;
import com.trinhvu.cart.repository.CartItemRepository;
import com.trinhvu.cart.repository.CartRepository;
import com.trinhvu.cart.utils.Constants;
import com.trinhvu.cart.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {
    private static final String CART_ITEM_UPDATED_MSG = "PRODUCT %s";
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartGetDetailVm addToCart(List<CartItemVm> cartItemVms) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String customerId = auth.getName();

        Cart cart = cartRepository.findByCustomerIdAndOrderIdIsNull(customerId).stream()
                .findFirst().orElse(null);
        Set<CartItem> existingCartItems = new HashSet<>();

        if (cart == null) {
            cart = Cart.builder()
                    .customerId(customerId)
                    .cartItems(existingCartItems)
                    .build();
            cart.setCreatedOn(ZonedDateTime.now());
        }else {
            existingCartItems = cartItemRepository.findAllByCart(cart);
        }

        for (CartItemVm cartItemVm: cartItemVms){
            CartItem cartItem = getCartItemByStockId(existingCartItems, cartItemVm.stockId());
            if (cartItem.getId() != null) {
                cartItem.setQuantity(cartItem.getQuantity() + cartItemVm.quantity());
            }else {
                cartItem.setQuantity(cartItem.getQuantity());
                cartItem.setCart(cart);
                cartItem.setStockId(cartItemVm.stockId());
                cart.getCartItems().add(cartItem);
            }
        }
        cart = cartRepository.save(cart);
//        cartItemRepository.saveAll(cart.getCartItems());
        return CartGetDetailVm.fromModel(cart);
    }

    private CartItem getCartItemByStockId(Set<CartItem> existingCartItems, Long stockId) {
        for (CartItem cartItem: existingCartItems) {
            if (cartItem.getStockId().equals(stockId)) {
                return cartItem;
            }
        }
        return new CartItem();
    }

    public List<CartGetDetailVm> getCartByCustomerId(String id) {
        return cartRepository.findByCustomerId(id).stream()
                .map(CartGetDetailVm::fromModel).toList();
    }

    public List<CartListVm> getCartWithFilter(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return cartRepository.findAll(pageable).stream().map(CartListVm::fromModel).toList();
    }

    public CartItemPutVm updateCartItem(CartItemVm cartItemVm, String customerId) {
        CartGetDetailVm currentCart = getLastCart(customerId);
        validateCart(currentCart, cartItemVm.stockId());

        Long cartId = currentCart.id();
        CartItem cartItem = cartItemRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.NON_EXISTING_CART_ITEM + cartId));

        int newQuantity = cartItemVm.quantity();
        cartItem.setQuantity(newQuantity);
        if(newQuantity == 0){
            cartItemRepository.delete(cartItem);
            return CartItemPutVm.fromModel(cartItem, String.format(CART_ITEM_UPDATED_MSG, "DELETED"));
        }else {
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            return CartItemPutVm.fromModel(savedCartItem, customerId);
        }
    }

    public void removeCartItemByStockId(Long id, String customerId) {
        CartGetDetailVm currentCart = getLastCart(customerId);
        validateCart(currentCart, id);
        cartItemRepository.deleteByCartIdAndStockId(currentCart.id(), id);
    }

    public void removeCartItemByStockIdList(List<Long> stockIds, String customerId) {
        CartGetDetailVm currentCart = getLastCart(customerId);
        stockIds.forEach(stockId -> validateCart(currentCart, stockId));
        cartItemRepository.deleteByCartIdAndStockIdIn(currentCart.id(), stockIds);
    }

    public Long countNumberItemInCart(String name) {
        Optional<Cart> cartOp = cartRepository.findByCustomerIdAndOrderIdIsNull(name).stream()
                .reduce((first, second) -> second);
        if (cartOp.isEmpty()) {
            return 0L;
        }
        var cart = cartOp.get();
//        Long total = cartItemRepository.countItemInCart(cart.getId());
        Long total = (long) cart.getCartItems().size();
        return total != null ? total : 0L;
    }

    public CartGetDetailVm getLastCart(String customerId) {
        return cartRepository.findByCustomerIdAndOrderIdIsNull(customerId).stream()
                .reduce((first, second) -> second)
                .map(CartGetDetailVm::fromModel).orElse(CartGetDetailVm.fromModel(new Cart()));
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
