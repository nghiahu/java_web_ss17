package com.example.session17.service.product;

import com.example.session17.entity.Product;
import com.example.session17.repository.product.ProductRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepositoryImp productRepositoryImp;

    @Override
    public List<Product> getAllProducts(int page, int pageSize) {
        return productRepositoryImp.listProduct(page, pageSize);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepositoryImp.getProduct(id);
    }

    @Override
    public int countProduct() {
        return productRepositoryImp.countProduct();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepositoryImp.deleteProduct(id);
    }

    @Override
    public void updateProduct(Product product) {
        productRepositoryImp.updateProduct(product);
    }

    @Override
    public void addProduct(Product product) {
        productRepositoryImp.addProduct(product);
    }
}
