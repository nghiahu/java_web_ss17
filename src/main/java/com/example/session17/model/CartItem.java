package com.example.session17.model;

import com.example.session17.entity.Product;
import com.example.session17.entity.ProductCart;

public class CartItem {
    private ProductCart cart;
    private Product product;

    public ProductCart getCart() {
        return cart;
    }

    public void setCart(ProductCart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
