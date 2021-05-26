package com.vadzimvincho.models.dto;

import com.vadzimvincho.models.entity.Role;

public class AppUserDto extends BaseEntityDto {
    private String login;
    private String password;
    private Role role;

    public AppUserDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
