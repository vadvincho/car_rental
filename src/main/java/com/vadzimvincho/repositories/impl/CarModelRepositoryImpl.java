package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.repositories.api.CarModelRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CarModelRepositoryImpl extends GenericRepositoryImpl<CarModel> implements CarModelRepository {
    public CarModelRepositoryImpl() {
        setTClassName(CarModel.class);
    }
}
