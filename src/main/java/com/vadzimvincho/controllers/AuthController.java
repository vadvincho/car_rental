package com.vadzimvincho.controllers;

import com.vadzimvincho.configs.security.jwt.JwtProvider;
import com.vadzimvincho.models.dto.AuthRequestDto;
import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.services.impl.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Value("${authorization}")
    public String AUTHORIZATION;
    @Value("${COOKIE_EXPIRY_IN_SECONDS}")
    private static int COOKIE_EXPIRY_IN_SECONDS;

    private JwtProvider jwtProvider;
    private UserService userService;

    public AuthController(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute("authDto") AuthRequestDto authDto) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("authDto") AuthRequestDto authDto, HttpServletResponse response, Model model) {
        AppUser appUser;
        try {
            appUser = userService.findByLoginAndPassword(authDto.getLogin(), authDto.getPassword());
        } catch (UsernameNotFoundException e) {
            model.addAttribute("loginError", e.getMessage());
            return "login";
        }
        if (appUser != null) {
            String token = jwtProvider.generateToken(appUser.getLogin());
            Cookie cookie = new Cookie(AUTHORIZATION, token);
            cookie.setMaxAge(COOKIE_EXPIRY_IN_SECONDS);
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @GetMapping
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/logout2")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTHORIZATION)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/login";
    }
}
