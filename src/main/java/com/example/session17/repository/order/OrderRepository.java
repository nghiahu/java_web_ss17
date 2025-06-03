package com.example.session17.repository.order;


import com.example.session17.entity.Orders;

public interface OrderRepository {
    void save(Orders order);
    int countOrders();
    double revenue();
}
