package com.vadzimvincho.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.vadzimvincho.configs.WebConfig;
import com.vadzimvincho.exceptions.Message;
import com.vadzimvincho.models.dto.CarDamageDto;
import com.vadzimvincho.models.dto.OrderDto;
import com.vadzimvincho.models.entity.Order;
import com.vadzimvincho.services.api.CarService;
import com.vadzimvincho.services.api.CustomerService;
import com.vadzimvincho.services.api.OrderService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration(classes = {WebConfig.class})
public class OrderControllerRestTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CarService carService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper modelMapper;


    private String objToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void create() throws Exception {
        Order newOrder = new Order();
        newOrder.setCustomer(customerService.getById(2l));
        newOrder.setCar(carService.getById(3l));
        newOrder.setStartTime(LocalDate.parse("2021-05-25"));
        newOrder.setEndTime(LocalDate.parse("2021-05-30"));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(newOrder, OrderDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(3, orderService.getAll().size());
        Assert.assertEquals(orderService.getById(3L).getOrderStatus().getStatus().name(), "PENDING");
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/orders")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price")
                        .value(750))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].customer.name")
                        .value("customer3"));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.name")
                        .value("customer2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(750));
    }

    @Test
    public void remove() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/orders/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert orderService.getAll().size() == 1;
    }

    @Test
    public void cancel() throws Exception {
        Message message = new Message("Test message");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/cancel/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(message))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert orderService.getById(1L).getOrderStatus().getId() == 4;
        assert orderService.getById(1L).getInfo().equals("Test message");
    }

    @Test
    public void confirm() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/confirm/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert orderService.getById(1L).getOrderStatus().getId() == 2;
    }

    @Test
    public void complete() throws Exception {
        CarDamageDto carDamageDto = new CarDamageDto();
        carDamageDto.setInfo("Test info");
        carDamageDto.setPrice(200);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/complete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(carDamageDto))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert orderService.getById(1L).getOrderStatus().getId() == 3;
    }


    @Test
    public void update() throws Exception {
        Order order = orderService.getById(1L);
        order.setInfo("any info");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(order, OrderDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert orderService.getById(1L).getInfo().equals("any info");
    }
}