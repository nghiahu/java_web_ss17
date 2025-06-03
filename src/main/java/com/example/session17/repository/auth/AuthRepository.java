package com.example.session17.repository.auth;

import com.example.session17.entity.Customer;

import java.util.List;

public interface AuthRepository {
    void register(Customer customer);
    Customer login(String username, String password);
    int countCustomers();
    List<Customer> searchCustomers(String keyword, int page, int size);
    int countSearchCustomers(String keyword);
    void updateStatus(int customerId, boolean status);
}
