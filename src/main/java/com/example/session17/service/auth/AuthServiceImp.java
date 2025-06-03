package com.example.session17.service.auth;

import com.example.session17.entity.Customer;
import com.example.session17.repository.auth.AuthRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private AuthRepositoryImp authRepositoryImp;

    @Override
    public void register(Customer customer) {
        authRepositoryImp.register(customer);
    }

    @Override
    public Customer login(String username, String password) {
        return authRepositoryImp.login(username, password);
    }
}
