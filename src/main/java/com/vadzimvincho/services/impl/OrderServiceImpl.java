package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.Order;
import com.vadzimvincho.repositories.api.OrderRepository;
import com.vadzimvincho.services.api.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl extends GenericServiceImpl<Order, OrderRepository> implements OrderService {
    public OrderServiceImpl(OrderRepository orderRepository) {
        super(orderRepository, LoggerFactory.getLogger(OrderServiceImpl.class));
    }
}
