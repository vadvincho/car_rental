package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.enums.EnumCarStatus;
import com.vadzimvincho.repositories.api.CarModelRepository;
import com.vadzimvincho.repositories.api.CarRepository;
import com.vadzimvincho.services.api.CarService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarServiceImpl extends GenericServiceImpl<Car, CarRepository> implements CarService {
    private final CarModelRepository carModelRepository;

    public CarServiceImpl(CarRepository carRepository, CarModelRepository carModelRepository) {
        super(carRepository, LoggerFactory.getLogger(CarServiceImpl.class));
        this.carModelRepository = carModelRepository;
    }

    @Override
    public List<Car> getByModel(Long id) {
        return this.ownRepository.getByModel(carModelRepository.getById(id));
    }

    @Override
    public List<Car> getByStatus(EnumCarStatus status) {
        return this.ownRepository.getByStatus(status);
    }

    @Override
    public List<Car> getAvailableCar() {
        return getByStatus(EnumCarStatus.AVAILABLE);
    }
}
