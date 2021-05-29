package com.vadzimvincho.models.dto;

public class AuthRequestDto {
    private String login;
    private String password;

    public AuthRequestDto() {
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
}
