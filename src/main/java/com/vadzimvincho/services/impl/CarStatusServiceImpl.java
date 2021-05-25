package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.CarStatus;
import com.vadzimvincho.repositories.api.CarStatusRepository;
import com.vadzimvincho.services.api.CarStatusService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarStatusServiceImpl extends GenericServiceImpl<CarStatus, CarStatusRepository> implements CarStatusService {
    public CarStatusServiceImpl(CarStatusRepository carStatusRepository) {
        super(carStatusRepository, LoggerFactory.getLogger(CarServiceImpl.class));
    }
}
