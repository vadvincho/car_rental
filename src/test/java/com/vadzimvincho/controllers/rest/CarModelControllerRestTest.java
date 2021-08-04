package com.vadzimvincho.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadzimvincho.configs.WebConfig;
import com.vadzimvincho.models.dto.CarModelDto;
import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.services.api.CarModelService;
import org.junit.Assert;
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
public class CarModelControllerRestTest {
    @Autowired
    CarModelService carModelService;
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
                                .get("/carModels")
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name")
                        .value("FOCUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name")
                        .value("A6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name")
                        .value("GOLF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].name")
                        .value("PASSAT"));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/carModels/1")
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("FOCUS"));
    }

    @Test
    public void add() throws Exception {
        CarModel carModel = new CarModel();
        carModel.setCarMake("test make");
        carModel.setName("test name");
        carModel.setYear(2001L);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/carModels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objToJson(modelMapper.map(carModel, CarModelDto.class)))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(7, carModelService.getAll().size());
        Assert.assertEquals("test name", carModelService.getById(5L).getName());
    }

    @Test
    public void remove() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/carModels/2")
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(5, carModelService.getAll().size());
    }

    @Test
    public void update() throws Exception {
        CarModel carModel = carModelService.getById(1L);
        carModel.setName("New name");
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/carModels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objToJson(modelMapper.map(carModel, CarModelDto.class)))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals("New name", carModelService.getById(1L).getName());
    }
}