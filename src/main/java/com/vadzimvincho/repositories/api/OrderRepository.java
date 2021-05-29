package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.models.entity.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Order>{
    List<Order> getByCustomer(Customer byId);
    List<Order> getByCar(Car car);
}
