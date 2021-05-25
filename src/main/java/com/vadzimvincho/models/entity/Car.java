package com.vadzimvincho.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "car")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_status_id")
    private CarStatus carStatus;
    @Column(name = "total_mileage")
    private long totalMileage;

    public Car() {
    }

    public Car(CarModel carModel, CarStatus carStatus, long totalMileage) {
        this.carModel = carModel;
        this.carStatus = carStatus;
        this.totalMileage = totalMileage;
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

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carModel=" + carModel +
                ", carStatus=" + carStatus +
                ", totalMileage=" + totalMileage +
                "}";
    }
}
