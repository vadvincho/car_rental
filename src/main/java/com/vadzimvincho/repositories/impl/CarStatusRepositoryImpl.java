package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.CarStatus;
import com.vadzimvincho.repositories.api.CarStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CarStatusRepositoryImpl extends GenericRepositoryImpl<CarStatus> implements CarStatusRepository {
    public CarStatusRepositoryImpl() {
        setTClassName(CarStatus.class);
    }
}
