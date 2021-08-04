package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.configs.security.jwt.JwtProvider;
import com.vadzimvincho.exceptions.Message;
import com.vadzimvincho.models.dto.AuthRequestDto;
import com.vadzimvincho.models.dto.AuthResponseDto;
import com.vadzimvincho.models.entity.AppUser;

import com.vadzimvincho.services.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(produces = "application/json")
public class AuthControllerRest {
    @Value("${authorization}")
    public String AUTHORIZATION;
    @Value("${COOKIE_EXPIRY_IN_SECONDS}")
    private String COOKIE_EXPIRY_IN_SECONDS;

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(AuthControllerRest.class);

    @Autowired
    public AuthControllerRest(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authDto, HttpServletResponse response) {
        AppUser appUser;
        try {
            appUser = userService.findByLoginAndPassword(authDto.getLogin(), authDto.getPassword());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(new Message("No user with this username and/or password was found"));
        }
        if (appUser != null) {
            String token = jwtProvider.generateToken(appUser.getLogin());
            Cookie cookie = new Cookie(AUTHORIZATION, token);
            cookie.setMaxAge(Integer.valueOf(COOKIE_EXPIRY_IN_SECONDS));
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(new Message("User "+ appUser.getLogin()+" login"));
    }

    @GetMapping("/logout2")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTHORIZATION)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return ResponseEntity.ok(new Message("Logout success"));
    }

    @PostMapping("/auth")
    public AuthResponseDto auth(@RequestBody AuthRequestDto request) {
        AppUser appUser = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(appUser.getLogin());
        logger.error("Token is existed!" + token);
        return new AuthResponseDto(token);
    }
}
