package com.vadzimvincho.models.dto;

import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.models.entity.CarStatus;

public class CarDto extends BaseEntityDto{
    private CarModel carModel;
    private CarStatus carStatus;
    private long totalMileage;

    public CarDto() {
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public long getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(long totalMileage) {
        this.totalMileage = totalMileage;
    }
}
