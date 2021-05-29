package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.models.entity.Order;
import com.vadzimvincho.repositories.api.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Order> implements OrderRepository {
    public OrderRepositoryImpl() {
        setTClassName(Order.class);
    }

    @Override
    public List<Order> getByCustomer(Customer customer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Join<Order, Customer> join = root.join("customer", JoinType.LEFT);
        query.select(root).where(criteriaBuilder.equal(join.get("id"), customer.getId()));
        List<Order> ordersByCustomer = entityManager.createQuery(query).getResultList();
        return ordersByCustomer;
    }

    @Override
    public List<Order> getByCar(Car car){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Join<Order, Customer> join = root.join("car", JoinType.LEFT);
        query.select(root).where(criteriaBuilder.equal(join.get("id"), car.getId()));
        List<Order> ordersByElectricCar = entityManager.createQuery(query).getResultList();
        return ordersByElectricCar;
    }
}



