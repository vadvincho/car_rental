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
@Table(name = "car_model")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarModel extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_make_id")
    private CarMake carMake;
    @Column(name = "name")
    private String name;
    @Column(name = "year")
    private Long year;
    @Column(name = "body_style")
    private String bodyStyle;
    @Column(name = "fuel")
    private String fuel;

    public CarModel() {
    }

    public CarModel(CarMake carMake, String name, Long year, String bodyStyle, String fuel) {
        this.carMake = carMake;
        this.name = name;
        this.year = year;
        this.bodyStyle = bodyStyle;
        this.fuel = fuel;
    }

    public CarMake getCarMake() {
        return carMake;
    }

    public void setCarMake(CarMake carMake) {
        this.carMake = carMake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + id +
                ", carMake=" + carMake +
                ", name='" + name + '\'' +
                ", year=" + year +
                "}";
    }
}
