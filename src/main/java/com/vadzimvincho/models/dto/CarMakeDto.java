package com.vadzimvincho.models.dto;

public class CarMakeDto extends BaseEntityDto {
    private String name;
    private String buildCountry;

    public CarMakeDto() {
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
}
