package com.example.session17.repository.auth;

import com.example.session17.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryImp implements AuthRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void register(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(customer);
        session.getTransaction().commit();
    }

    @Override
    public Customer login(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql = "FROM Customer WHERE username = :username AND password = :password";
        Customer customer = session.createQuery(hql, Customer.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        session.getTransaction().commit();
        return customer;
    }

}
