package com.example.session17.repository.auth;

import com.example.session17.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public int countCustomers() {
        Session session = sessionFactory.openSession();
        Long count = session.createQuery("SELECT COUNT(c) FROM Customer c", Long.class)
                .uniqueResult();
        return count != null ? count.intValue() : 0;
    }
    @Override
    public List<Customer> searchCustomers(String keyword, int page, int size) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Customer WHERE username LIKE :keyword OR email LIKE :keyword";
        List<Customer> customers = session.createQuery(hql, Customer.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        session.close();
        return customers;
    }
    @Override
    public int countSearchCustomers(String keyword) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT COUNT(c) FROM Customer c WHERE username LIKE :keyword OR email LIKE :keyword";
        Long count = session.createQuery(hql, Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .uniqueResult();
        session.close();
        return count != null ? count.intValue() : 0;
    }
    @Override
    public void updateStatus(int customerId, boolean status) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, customerId);
        if (customer != null) {
            customer.setStatus(status);
            session.update(customer);
        }
        session.getTransaction().commit();
    }

}
