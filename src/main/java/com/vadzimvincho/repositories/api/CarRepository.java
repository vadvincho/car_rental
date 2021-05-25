package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.enums.EnumCarStatus;

import java.util.List;

public interface CarRepository extends GenericRepository<Car>{
    List<Car> getByModel(CarModel carModel);
    List<Car> getByStatus(EnumCarStatus status);
}
