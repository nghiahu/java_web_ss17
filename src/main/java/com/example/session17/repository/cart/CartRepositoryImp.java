package com.example.session17.repository.cart;

import com.example.session17.entity.ProductCart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepositoryImp implements CartRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<ProductCart> getAllCarts(Long userId) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<ProductCart> list = session.createQuery(
                        "FROM ProductCart WHERE customerId = :userId", ProductCart.class)
                .setParameter("userId", userId)
                .getResultList();
        tx.commit();
        return list;
    }

    @Override
    public void addToCart(ProductCart productCart) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.save(productCart);
        tx.commit();
    }

    @Override
    public void updateQuantity(Long cartId, int quantity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        ProductCart cart = session.get(ProductCart.class, cartId);
        if (cart != null) {
            cart.setQuantity(quantity);
            session.update(cart);
        }
        tx.commit();
    }

    @Override
    public void deleteItem(Long cartId) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        ProductCart cart = session.get(ProductCart.class, cartId);
        if (cart != null) {
            session.delete(cart);
        }
        tx.commit();
    }
}

