package com.example.session17.service.product;

import com.example.session17.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(int page, int pageSize);
    Product getProduct(Long id);
    int countProduct();
    void deleteProduct(Long id);
    void updateProduct(Product product);
    void addProduct(Product product);
}
