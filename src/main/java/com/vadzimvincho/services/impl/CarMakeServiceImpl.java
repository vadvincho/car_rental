package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.CarMake;
import com.vadzimvincho.repositories.api.CarMakeRepository;
import com.vadzimvincho.services.api.CarMakeService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarMakeServiceImpl extends GenericServiceImpl<CarMake, CarMakeRepository> implements CarMakeService {
    public CarMakeServiceImpl(CarMakeRepository carMakeRepository) {
        super(carMakeRepository, LoggerFactory.getLogger(CarServiceImpl.class));
    }
}
