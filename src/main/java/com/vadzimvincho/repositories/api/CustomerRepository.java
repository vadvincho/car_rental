package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.models.entity.Order;

public interface CustomerRepository extends GenericRepository<Customer> {
    Customer getByUser(AppUser user);
}
