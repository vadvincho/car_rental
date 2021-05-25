package com.vadzimvincho.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequestDto {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
