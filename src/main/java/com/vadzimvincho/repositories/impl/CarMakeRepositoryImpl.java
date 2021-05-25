package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.CarMake;
import com.vadzimvincho.repositories.api.CarMakeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CarMakeRepositoryImpl extends GenericRepositoryImpl<CarMake> implements CarMakeRepository {
    public CarMakeRepositoryImpl() {
        setTClassName(CarMake.class);
    }
}
