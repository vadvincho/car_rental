package com.vadzimvincho.services.impl;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.exceptions.Message;
import com.vadzimvincho.models.dto.CarDamageDto;
import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.models.entity.Order;
import com.vadzimvincho.models.enums.EnumCarStatus;
import com.vadzimvincho.models.enums.EnumOrderStatus;
import com.vadzimvincho.repositories.api.*;
import com.vadzimvincho.services.api.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl extends GenericServiceImpl<Order, OrderRepository> implements OrderService {
    private final static int pricePerDay = 20;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CarStatusRepository carStatusRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository,
                            CarRepository carRepository, OrderStatusRepository orderStatusRepository,
                            CarStatusRepository carStatusRepository) {
        super(orderRepository, LoggerFactory.getLogger(OrderServiceImpl.class));
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.carStatusRepository = carStatusRepository;
    }

    @Override
    public void create(Order order) throws DaoException {
        Car car = carRepository.getById(order.getCar().getId());
//        order.setCustomer(customerRepository.getById(order.getCustomer().getId()));
        if (!car.getCarStatus().getStatus().equals(EnumCarStatus.AVAILABLE)) {
            logger.error("Car is not available");
            throw new RuntimeException("Car is not available");
        } else {
            car.setCarStatus(carStatusRepository.getByEnumName(EnumCarStatus.RENTING));
            order.setCar(car);
            order.setPrice(ChronoUnit.DAYS.between(order.getStartTime(), order.getEndTime()) * pricePerDay);
            order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.PENDING));
            ownRepository.add(order);
            logger.info("Order successfully created");
        }
    }

    @Override
    public void confirm(Order order) throws DaoException {
        order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.EXECUTING));
        ownRepository.update(order);
        logger.info("Order confirmed");
    }

    @Override
    public void cancel(Order order, Message message) throws DaoException {
        order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.CANCELED));
        order.setInfo(message.getMessage());
        order.getCar().setCarStatus(carStatusRepository.getByEnumName(EnumCarStatus.AVAILABLE));
        ownRepository.update(order);
        logger.info("Order canceled");
    }

    @Override
    public void complete(Order order, CarDamageDto carDamage) throws DaoException {
        if (carDamage != null) {
            order.setInfo(carDamage.getInfo());
            order.setPrice(order.getPrice() + carDamage.getPrice());
        }
        Customer customer = order.getCustomer();
        double newBalance = customer.getBalance() - order.getPrice();
        customer.setBalance(newBalance);
        customerRepository.update(customer);
        order.setCustomer(customer);
        if (newBalance >= 0) {
            order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.COMPLETED));
            logger.info("Order completed");
        } else {
            order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.NOT_PAID));
            logger.info("Order not paid. Not enough money.");
        }
        ownRepository.update(order);
    }


    @Override
    public List<Order> getByCustomer(Long customerId) throws DaoException {
        return ownRepository.getByCustomer(customerRepository.getById(customerId));
    }

    @Override
    public List<Order> getByCar(Long carId) throws DaoException {
        return ownRepository.getByCar(carRepository.getById(carId));
    }
}
