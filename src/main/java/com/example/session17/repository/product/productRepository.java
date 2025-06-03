package com.example.session17.repository.product;

import com.example.session17.entity.Product;
import java.util.List;

public interface productRepository {
    List<Product> listProduct(int page, int pageSize);
    Product getProduct(Long id);
}
