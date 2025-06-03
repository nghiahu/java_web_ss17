package com.example.session17.repository.auth;

import com.example.session17.entity.Customer;

public interface AuthRepository {
    void register(Customer customer);
    Customer login(String username, String password);
}
