package com.example.session17.service.cart;

import com.example.session17.entity.ProductCart;
import com.example.session17.repository.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<ProductCart> getAllCarts(Long userId) {
        return cartRepository.getAllCarts(userId);
    }

    @Override
    public void addToCart(ProductCart productCart) {
        cartRepository.addToCart(productCart);
    }

    @Override
    public void updateQuantity(Long cartId, int quantity) {
        cartRepository.updateQuantity(cartId, quantity);
    }

    @Override
    public void deleteItem(Long cartId) {
        cartRepository.deleteItem(cartId);
    }

    @Override
    public void removeCart(Long userId) {
        cartRepository.removeCart(userId);
    }
}
