package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.dto.CustomerBalanceDto;
import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.repositories.api.CustomerRepository;
import com.vadzimvincho.repositories.api.OrderRepository;
import com.vadzimvincho.repositories.api.OrderStatusRepository;
import com.vadzimvincho.services.api.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl extends GenericServiceImpl<Customer, CustomerRepository> implements CustomerService {
    private OrderStatusRepository orderStatusRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        super(customerRepository, LoggerFactory.getLogger(CustomerServiceImpl.class));
        this.orderRepository = orderRepository;
    }

    @Override
    public void topUpBalance(Customer customer, CustomerBalanceDto money) {
        Double newBalance = customer.getBalance() + money.getMoney();
        customer.setBalance(newBalance);
        ownRepository.update(customer);
    }

    @Override
    public Customer getCustomerByUser(AppUser user) {
        return ownRepository.getByUser(user);
    }
}
