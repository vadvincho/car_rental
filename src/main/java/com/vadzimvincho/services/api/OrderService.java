package com.vadzimvincho.services.api;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.exceptions.Message;
import com.vadzimvincho.models.dto.CarDamageDto;
import com.vadzimvincho.models.entity.Order;

import java.util.List;

public interface OrderService extends GenericService<Order> {
    void create(Order order);

    void confirm(Order order);

    void cancel(Order order, Message message);

    void complete(Order order, CarDamageDto carDamage);

    List<Order> getByCustomer(Long customerId);

    List<Order> getByCar(Long electricCarId);
}
