package com.vadzimvincho.services.api;

import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.enums.EnumCarStatus;

import java.util.List;

public interface CarService extends GenericService<Car>{
    List<Car> getByModel(Long id);
    List<Car> getByStatus(EnumCarStatus status);
    List<Car> getAvailableCar();
}
