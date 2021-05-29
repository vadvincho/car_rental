package com.vadzimvincho.models.dto;

public class CarDto extends BaseEntityDto {
    private CarModelDto carModel;
    private CarStatusDto carStatus;
    private long totalMileage;

    public CarDto() {
    }

    public CarModelDto getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelDto carModel) {
        this.carModel = carModel;
    }

    public CarStatusDto getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatusDto carStatus) {
        this.carStatus = carStatus;
    }

    public long getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(long totalMileage) {
        this.totalMileage = totalMileage;
    }
}
