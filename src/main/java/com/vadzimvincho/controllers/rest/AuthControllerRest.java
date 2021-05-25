package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.models.dto.AuthRequestDto;
import com.vadzimvincho.models.dto.AuthResponseDto;
import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.configs.security.jwt.JwtProvider;
import com.vadzimvincho.services.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(produces = "application/json")
public class AuthControllerRest {

    private JwtProvider jwtProvider;
    private UserService userService;

    public AuthControllerRest(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public AuthResponseDto auth(@RequestBody AuthRequestDto request) {
        AppUser appUser = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(appUser.getLogin());
        log.error("Token is existed!" + token);
        return new AuthResponseDto(token);
    }
}
