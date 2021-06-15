package com.vadzimvincho.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
public class TestSecurityController {

    private static final Logger LOGGER= LoggerFactory.getLogger(TestSecurityController.class);

    @GetMapping("/admin/get")
    public String getAdmin() {
        LOGGER.info("Доступен только админу!");
        return "Hi admin";
    }

    @GetMapping("/user/get")
    public String getUser() {
        LOGGER.info("Доступен только юзеру!");
        return "Hi user";
    }
}
