package com.vadzimvincho.services.impl;

import com.vadzimvincho.exceptions.CarStatusException;
import com.vadzimvincho.exceptions.DaoException;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl extends GenericServiceImpl<Order, OrderRepository> implements OrderService {
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
        if (!car.getCarStatus().getStatus().equals(EnumCarStatus.AVAILABLE)) {
            logger.error("Car is not available");
            throw new RuntimeException("Car is not available");
        } else {
            car.setCarStatus(carStatusRepository.getByEnumName(EnumCarStatus.RENTING));
            order.setCar(car);
            order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.EXECUTING));
            ownRepository.add(order);
            logger.info("Order successfully created");
        }
    }

    @Override
    public void cancel(Order order) throws DaoException {
        order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.CANCELED));
        order.getCar().setCarStatus(carStatusRepository.getByEnumName(EnumCarStatus.AVAILABLE));
        ownRepository.update(order);
        logger.info("Order canceled");
    }

    @Override
    public void complete(Order order) throws DaoException {
//        order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.COMPLETED));
        order.setEndTime(LocalDate.now());
        setPriceToOrder(order);
        Car car = order.getCar();
//        carDamage
        payForOrder(order);
        ownRepository.update(order);
        logger.info("Order completed");
    }

    private void payForOrder(Order order) {
        Customer customer = order.getCustomer();
        customer.setBalance(customer.getBalance() - order.getPrice());
        order.setOrderStatus(orderStatusRepository.getByEnumName(EnumOrderStatus.COMPLETED));
    }

    @Override
    public List<Order> getByCustomer(Long customerId) throws DaoException {
        return ownRepository.getByCustomer(customerRepository.getById(customerId));
    }

    @Override
    public List<Order> getByCar(Long carId) throws DaoException {
        return ownRepository.getByCar(carRepository.getById(carId));
    }

    private Order setPriceToOrder(Order order) {
        // set price
        return order;
    }

    private long getUsedTime(LocalDateTime startTime, LocalDateTime endTime) {
        long usedTimeMinutes = ChronoUnit.MINUTES.between(startTime, endTime);
        long usedTimeHours = usedTimeMinutes / 60;
        return (usedTimeHours % 60) > 0 ? usedTimeHours + 1 : usedTimeHours;
    }
}
