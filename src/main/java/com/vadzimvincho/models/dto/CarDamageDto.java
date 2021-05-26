package com.vadzimvincho.models.dto;

public class CarDamageDto extends BaseEntityDto {
    private String info;
    private double price;

    public CarDamageDto() {
    }

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
}
