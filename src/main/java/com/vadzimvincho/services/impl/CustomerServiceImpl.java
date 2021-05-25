package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.repositories.api.CustomerRepository;
import com.vadzimvincho.services.api.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl extends GenericServiceImpl<Customer, CustomerRepository> implements CustomerService {

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super(customerRepository, LoggerFactory.getLogger(CustomerServiceImpl.class));
    }
}
