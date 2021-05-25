package com.vadzimvincho.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "car_make")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarMake extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "build_country")
    private String buildCountry;

    public CarMake() {
    }

    public CarMake(String name, String buildCountry) {
        this.name = name;
        this.buildCountry = buildCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildCountry() {
        return buildCountry;
    }

    public void setBuildCountry(String buildCountry) {
        this.buildCountry = buildCountry;
    }

    @Override
    public String toString() {
        return "CarMake{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "}";
    }
}
