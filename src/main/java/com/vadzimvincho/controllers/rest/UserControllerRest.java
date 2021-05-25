package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.models.dto.RegistrationRequestDto;
import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.services.impl.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
public class UserControllerRest {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserControllerRest(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequestDto request) {
        AppUser appUser = modelMapper.map(request, AppUser.class);
        userService.saveUser(appUser);
        return "OK";
    }
}
