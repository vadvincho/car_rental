package com.vadzimvincho.models.dto;

public abstract class BaseEntityDto {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
