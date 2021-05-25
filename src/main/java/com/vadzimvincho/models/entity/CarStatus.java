package com.vadzimvincho.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vadzimvincho.models.enums.EnumCarStatus;

import javax.persistence.*;

@Entity
@Table(name = "car_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarStatus extends BaseEntity {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumCarStatus status;

    public CarStatus() {
    }

    public EnumCarStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCarStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CarStatus{" +
                "id=" + id +
                ", status=" + status +
                "}";
    }
}
