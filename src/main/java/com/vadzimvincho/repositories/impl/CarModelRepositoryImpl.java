package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.CarMake;
import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.repositories.api.CarModelRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CarModelRepositoryImpl extends GenericRepositoryImpl<CarModel> implements CarModelRepository {
    public CarModelRepositoryImpl() {
        setTClassName(CarModel.class);
    }

    @Override
    public List<CarModel> getByCarMake(CarMake carMake) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarModel> query = criteriaBuilder.createQuery(CarModel.class);
        Root<CarModel> root = query.from(CarModel.class);
        Join<CarModel, CarMake> join = root.join("carMake", JoinType.LEFT);
        query.select(root).where(criteriaBuilder.equal(join.get("name"), carMake.getName()));
        List<CarModel> carModelList = entityManager.createQuery(query).getResultList();
        return carModelList;
    }
}
