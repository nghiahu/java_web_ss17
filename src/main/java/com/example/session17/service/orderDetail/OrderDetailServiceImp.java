package com.example.session17.service.orderDetail;

import com.example.session17.entity.OrderDetail;
import com.example.session17.repository.orderDetail.OrderDetailRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImp implements OrderDetailService {
    @Autowired
    private OrderDetailRepositoryImp orderDetailRepositoryImp;

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepositoryImp.save(orderDetail);
    }
}
