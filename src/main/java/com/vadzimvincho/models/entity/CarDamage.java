package com.vadzimvincho.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "car_damage")
public class CarDamage extends BaseEntity {
    @Column(name = "info")
    private String info;
    @Column(name = "price")
    private double price;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarDamage{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", price=" + price +
                "} " + super.toString();
    }
}
