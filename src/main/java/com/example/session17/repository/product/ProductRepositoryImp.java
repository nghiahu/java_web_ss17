package com.example.session17.repository.product;

import com.example.session17.entity.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImp implements productRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

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

    @Override
    public int countProduct() {
        Session session = sessionFactory.openSession();
        Long count = session.createQuery("SELECT COUNT(p) FROM Product p", Long.class).uniqueResult();
        return count.intValue();
    }

    @Override
    public void deleteProduct(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        if (product != null) {
            session.delete(product);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateProduct(Product product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(product);
        tx.commit();
        session.close();
    }

    @Override
    public void addProduct(Product product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(product);
        tx.commit();
        session.close();
    }
}
