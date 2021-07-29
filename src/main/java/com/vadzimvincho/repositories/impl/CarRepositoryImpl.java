package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.models.entity.CarStatus;
import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.enums.EnumCarStatus;
import com.vadzimvincho.repositories.api.CarRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class CarRepositoryImpl extends GenericRepositoryImpl<Car> implements CarRepository {
    CarRepositoryImpl() {
        setTClassName(Car.class);
    }

    @Override
    public List<Car> getByModel(CarModel carModel) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> query = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);
        Join<Car, CarModel> join1 = root.join("carModel", JoinType.LEFT);
        query.select(root).where(criteriaBuilder.equal(join1.get("name"), carModel.getName()));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Car> getByStatus(EnumCarStatus status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> query = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);
        Join<Car, CarStatus> join = root.join("carStatus", JoinType.LEFT);
        query.select(root).where(criteriaBuilder.equal(join.get("status"), status));
        return entityManager.createQuery(query).getResultList();
    }
}
