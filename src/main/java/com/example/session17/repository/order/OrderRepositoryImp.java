package com.example.session17.repository.order;

import com.example.session17.entity.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImp implements OrderRepository {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Orders orders) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.save(orders);
        tx.commit();
    }

    @Override
    public int countOrders() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Long count = session.createQuery("SELECT COUNT(o) FROM Orders o", Long.class).uniqueResult();
        tx.commit();
        return count.intValue();
    }

    @Override
    public double revenue() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Double totalRevenue = session.createQuery("SELECT SUM(o.totalMoney) FROM Orders o", Double.class).uniqueResult();
        tx.commit();
        return totalRevenue != null ? totalRevenue : 0.0;
    }

}
