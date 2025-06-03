package com.example.session17.service.auth;

import com.example.session17.entity.Customer;

public interface AuthService {
    void register(Customer customer);
    Customer login(String username, String password);
}
