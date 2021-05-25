package com.vadzimvincho.models.dto;

import com.vadzimvincho.models.enums.EnumCarStatus;

public class CarStatusDto extends BaseEntityDto{
    private EnumCarStatus status;

    public CarStatusDto() {
    }

    public EnumCarStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCarStatus status) {
        this.status = status;
    }
}
