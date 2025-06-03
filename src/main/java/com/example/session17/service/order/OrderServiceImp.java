package com.example.session17.service.order;

import com.example.session17.entity.Orders;
import com.example.session17.repository.order.OrderRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepositoryImp orderRepositoryImp;

    @Override
    public void save(Orders orders) {
        orderRepositoryImp.save(orders);
    }

    @Override
    public int countOrders() {
        return orderRepositoryImp.countOrders();
    }

    @Override
    public double revenue() {
        return orderRepositoryImp.revenue();
    }
}
