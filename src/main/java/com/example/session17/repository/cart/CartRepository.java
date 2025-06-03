package com.example.session17.repository.cart;

import com.example.session17.entity.ProductCart;

import java.util.List;

public interface CartRepository {
    List<ProductCart> getAllCarts(Long userId);
    void addToCart(ProductCart productCart);
    void updateQuantity(Long cartId, int quantity);
    void deleteItem(Long cartId);
}
