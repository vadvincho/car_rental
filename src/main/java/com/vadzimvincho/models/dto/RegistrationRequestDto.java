package com.vadzimvincho.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class RegistrationRequestDto {

    @NotEmpty
    @Size(min = 3, message = "Не меньше 5 знаков")
    private String login;
    @Size(min = 3, message = "Не меньше 5 знаков")
    @NotEmpty
    private String password;
    @Size(min = 3, message = "Не меньше 5 знаков")
    @NotEmpty
    private String passwordConfirm;

    public RegistrationRequestDto() {
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
