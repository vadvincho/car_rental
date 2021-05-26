package com.vadzimvincho.models.dto;

import com.vadzimvincho.models.entity.AppUser;

public class ManagerDto extends BaseEntityDto {
    private String name;
    private String phoneNumber;
    private AppUser user;

    public ManagerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
