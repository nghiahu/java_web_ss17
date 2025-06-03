package com.example.session17.service.auth;

import com.example.session17.entity.Customer;
import com.example.session17.repository.auth.AuthRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public int countCustomers() {
        return authRepositoryImp.countCustomers();
    }

    @Override
    public List<Customer> searchCustomers(String keyword, int page, int size) {
        return authRepositoryImp.searchCustomers(keyword, page, size);
    }

    @Override
    public int countSearchCustomers(String keyword) {
        return authRepositoryImp.countSearchCustomers(keyword);
    }

    @Override
    public void updateStatus(int customerId, boolean status) {
        authRepositoryImp.updateStatus(customerId, status);
    }
}
