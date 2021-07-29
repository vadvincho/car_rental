package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.repositories.api.CustomerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class CustomerRepositoryImpl extends GenericRepositoryImpl<Customer> implements CustomerRepository {
    public CustomerRepositoryImpl() {
        setTClassName(Customer.class);
    }

    @Override
    public Customer getByUser(AppUser user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        Join<Customer, AppUser> join = root.join("user", JoinType.LEFT);
        query.select(root).where(criteriaBuilder.equal(join.get("id"), user.getId()));
        return entityManager.createQuery(query).getSingleResult();
    }
}
