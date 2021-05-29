package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.CarStatus;
import com.vadzimvincho.models.enums.EnumCarStatus;
import com.vadzimvincho.repositories.api.CarStatusRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class CarStatusRepositoryImpl extends GenericRepositoryImpl<CarStatus> implements CarStatusRepository {
    public CarStatusRepositoryImpl() {
        setTClassName(CarStatus.class);
    }

    @Override
    public CarStatus getByEnumName(EnumCarStatus enumCarStatus) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarStatus> query = criteriaBuilder.createQuery(CarStatus.class);
        Root<CarStatus> root = query.from(CarStatus.class);
        query.select(root).where(criteriaBuilder.equal(root.get("status"), enumCarStatus));
        CarStatus carStatus = entityManager.createQuery(query).getSingleResult();
        return carStatus;
    }

}
