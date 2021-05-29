package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.models.entity.Order;
import com.vadzimvincho.repositories.api.CustomerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomerRepositoryImpl extends GenericRepositoryImpl<Customer> implements CustomerRepository {
    public CustomerRepositoryImpl() {
        setTClassName(Customer.class);
    }
}
