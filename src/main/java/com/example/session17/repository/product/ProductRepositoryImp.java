package com.example.session17.repository.product;

import com.example.session17.entity.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImp implements productRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Product> listProduct(int page, int pageSize) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Product> query = session.createQuery("FROM com.example.session17.entity.Product", Product.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Product getProduct(Long id) {
        Session session = sessionFactory.openSession();
       return session.get(Product.class, id);
    }
}
