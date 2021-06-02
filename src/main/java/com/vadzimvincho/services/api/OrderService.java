package com.vadzimvincho.services.api;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.exceptions.Message;
import com.vadzimvincho.models.dto.CarDamageDto;
import com.vadzimvincho.models.entity.Order;

import java.util.List;

public interface OrderService extends GenericService<Order> {
    void create(Order order) throws DaoException;

    void confirm(Order order) throws DaoException;

    void cancel(Order order, Message message) throws DaoException;

    void complete(Order order, CarDamageDto carDamage) throws DaoException;

    List<Order> getByCustomer(Long customerId) throws DaoException;

    List<Order> getByCar(Long electricCarId) throws DaoException;
}
