package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.repositories.api.CustomerRepository;
import org.springframework.stereotype.Repository;


@Repository
public class CustomerRepositoryImpl extends GenericRepositoryImpl<Customer> implements CustomerRepository {
    public CustomerRepositoryImpl(){
        setTClassName(Customer.class);
    }
}
