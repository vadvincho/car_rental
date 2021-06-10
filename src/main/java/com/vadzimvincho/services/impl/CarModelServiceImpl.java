package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.repositories.api.CarModelRepository;
import com.vadzimvincho.services.api.CarModelService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarModelServiceImpl extends GenericServiceImpl<CarModel, CarModelRepository> implements CarModelService {
    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository) {
        super(carModelRepository, LoggerFactory.getLogger(CarServiceImpl.class));
    }
}
