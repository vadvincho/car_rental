package com.vadzimvincho.controllers;

import com.vadzimvincho.models.dto.RegistrationRequestDto;
import com.vadzimvincho.models.entity.AppUser;

import com.vadzimvincho.services.api.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register-user")
    String getRegisterForm(@ModelAttribute("requestDto") RegistrationRequestDto requestDto) {
        return "registration-form";
    }

    @PostMapping("/register-user")
    public String addUser(@ModelAttribute("requestDto") @Valid RegistrationRequestDto requestDto,
                          BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Password and password confirmation do not match");
            return "registration-form";
        }
        AppUser appUser = modelMapper.map(requestDto, AppUser.class);
        if (userService.findByLogin(requestDto.getLogin()) != null) {
            model.addAttribute("usernameError", "The user with the same name already exists");
            return "registration-form";
        }
        userService.saveUser(appUser);
        return "redirect:/";
    }
}
