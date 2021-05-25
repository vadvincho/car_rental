package com.vadzimvincho.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
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
}
