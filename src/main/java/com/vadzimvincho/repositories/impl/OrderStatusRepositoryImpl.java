package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.OrderStatus;
import com.vadzimvincho.models.enums.EnumOrderStatus;
import com.vadzimvincho.repositories.api.OrderStatusRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class OrderStatusRepositoryImpl extends GenericRepositoryImpl<OrderStatus> implements OrderStatusRepository {
    public OrderStatusRepositoryImpl() {
        setTClassName(OrderStatus.class);
    }

    @Override
    public OrderStatus getByEnumName(EnumOrderStatus enumOrderStatus) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderStatus> query = criteriaBuilder.createQuery(OrderStatus.class);
        Root<OrderStatus> root = query.from(OrderStatus.class);
        query.select(root).where(criteriaBuilder.equal(root.get("status"), enumOrderStatus));
        return entityManager.createQuery(query).getSingleResult();
    }

}
