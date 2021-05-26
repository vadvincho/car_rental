package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.Order;
import com.vadzimvincho.repositories.api.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Order> implements OrderRepository {
    public OrderRepositoryImpl() {
        setTClassName(Order.class);
    }
}
