package com.vadzimvincho.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadzimvincho.configs.WebConfig;
import com.vadzimvincho.models.dto.AppUserDto;
import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.repositories.api.RoleRepository;
import com.vadzimvincho.services.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration(classes = {WebConfig.class})
public class UserControllerRestTest {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper modelMapper;

    private String objToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].login")
                        .value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].login")
                        .value("testuser1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].login")
                        .value("testuser2"));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/users/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login")
                        .value("admin"));
    }

    @Test
    public void add() throws Exception {
        AppUser user = new AppUser();
        user.setRole(roleRepository.getById(1L));
        user.setLogin("test");
        user.setEmail("test@test.com");
        user.setPassword("12345");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(user, AppUserDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert userService.getAll().size() == 4;
        assert userService.getById(4L).getLogin().equals("test");
    }

    @Test
    public void remove() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert userService.getAll().size() == 2;
    }

    @Test
    public void update() throws Exception {
        AppUser user = userService.getById(1L);
        user.setLogin("test");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(user, AppUserDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert userService.getById(1L).getLogin().equals("test");
    }
}