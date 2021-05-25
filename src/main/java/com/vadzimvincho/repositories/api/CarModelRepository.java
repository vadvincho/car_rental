package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.CarMake;
import com.vadzimvincho.models.entity.CarModel;

import java.util.List;

public interface CarModelRepository extends GenericRepository<CarModel> {
    List<CarModel> getByCarMake(CarMake carMake);
}