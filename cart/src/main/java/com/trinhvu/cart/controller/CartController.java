package com.trinhvu.cart.controller;

import com.trinhvu.cart.service.CartService;
import com.trinhvu.cart.viewmodel.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartGetDetailVm> createCart(@RequestBody List<CartItemVm> cartItemVm) {
        return ResponseEntity.ok(cartService.addToCart(cartItemVm));
    }

    @GetMapping
    public ResponseEntity<List<CartListVm>> listCarts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return ResponseEntity.ok(cartService.getCartWithFilter(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CartGetDetailVm>> getCartByCustomerId(@PathVariable String id) {
       return ResponseEntity.ok(cartService.getCartByCustomerId(id));
    }

    @PutMapping
    public ResponseEntity<CartItemPutVm> updateCart(@Valid @RequestBody CartItemVm cartItemVm)  {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(cartService.updateCartItem(cartItemVm, auth.getName()));
    }

    @DeleteMapping("/cart-item")
    public ResponseEntity<Void> removeCartItemByStockId(@RequestParam Long id) {
        cartService.removeCartItemByStockId(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart-item/multi-delete")
    public ResponseEntity<Void> removeCartItemByStockIdList(@RequestParam List<Long> stockIds) {
//        cartService.removeCartItemByStockIdList(stockIds);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count-cart-items")
    public ResponseEntity<Long> getNumberItemInCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok().body(0L);
        }
        return ResponseEntity.ok(cartService.countNumberItemInCart(principal.getName()));
    }

    @GetMapping("/last-cart")
    public ResponseEntity<Long> getLastCart(Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.ok().body(0L);
//        }
//        return ResponseEntity.ok(cartService.getLastCart(principal.getName()));
        return null;
    }
}
