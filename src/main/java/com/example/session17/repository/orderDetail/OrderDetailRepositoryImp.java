package com.example.session17.repository.orderDetail;

import com.example.session17.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailRepositoryImp implements OrderDetailRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(OrderDetail orderDetail) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.save(orderDetail);
        tx.commit();
    }
}
