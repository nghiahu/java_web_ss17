package com.example.session17.service.order;

import com.example.session17.entity.Orders;

public interface OrderService {
    void save(Orders orders);
    int countOrders();
    double revenue();
}
