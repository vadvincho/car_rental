package com.vadzimvincho.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadzimvincho.configs.WebConfig;
import com.vadzimvincho.models.dto.CarDto;
import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.entity.CarModel;
import com.vadzimvincho.services.api.CarModelService;
import com.vadzimvincho.services.api.CarService;
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
public class CarControllerRestTest {
    @Autowired
    CarModelService carModelService;
    @Autowired
    CarService carService;
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
                        .get("/cars")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].carModel.name")
                        .value("FOCUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].carModel.name")
                        .value("A6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].carModel.name")
                        .value("GOLF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].carModel.name")
                        .value("PASSAT"));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/cars/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carModel.name")
                        .value("FOCUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carModel.year")
                        .value("2015"));
    }

    @Test
    public void add() throws Exception {
        CarModel carModel = carModelService.getById(2l);
        Car car = new Car();
        car.setCarModel(carModel);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(car, CarDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(7, carService.getAll().size());
        Assert.assertEquals("A6",carService.getById(7L).getCarModel().getName());
    }

    @Test
    public void remove() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/cars/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(5,carService.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Car car = carService.getById(1L);
        car.setTotalMileage(20000);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(car, CarDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(20000,carService.getById(1L).getTotalMileage());
    }
}