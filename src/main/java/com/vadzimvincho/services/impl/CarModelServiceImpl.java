package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.repositories.api.CarMakeRepository;
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
    private final CarMakeRepository carMakeRepository;

    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository, CarMakeRepository carMakeRepository) {
        super(carModelRepository, LoggerFactory.getLogger(CarServiceImpl.class));
        this.carMakeRepository = carMakeRepository;
    }

    @Override
    public List<CarModel> getByCarMake(Long id) {
        return ownRepository.getByCarMake(carMakeRepository.getById(id));
    }
}
