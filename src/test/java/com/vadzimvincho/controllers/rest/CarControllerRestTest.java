package com.vadzimvincho.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadzimvincho.configs.WebConfig;
import com.vadzimvincho.models.dto.CustomerBalanceDto;
import com.vadzimvincho.models.dto.CustomerDto;
import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.services.api.CustomerService;
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
public class CarControllerRestTest {
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;
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
                        .get("/customers")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name")
                        .value("customer1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name")
                        .value("customer2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name")
                        .value("customer3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].name")
                        .value("customer4"));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/customers/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("customer1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber")
                        .value("+375285554556"));
    }

    @Test
    public void add() throws Exception {
        AppUser user = userService.getById(2L);
        Customer customer = new Customer();
        customer.setName("New customer");
        customer.setPhoneNumber("+1111111111");
        customer.setUser(user);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(customer, CustomerDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert customerService.getAll().size() == 5;
        assert customerService.getById(5L).getName().equals("New customer");
        assert customerService.getById(5L).getPhoneNumber().equals("+1111111111");
    }

    @Test
    public void remove() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/customers/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert customerService.getAll().size() == 3;
    }

    @Test
    public void update() throws Exception {
        Customer customer = customerService.getById(1L);
        customer.setName("New name");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(modelMapper.map(customer, CustomerDto.class)))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert customerService.getById(1L).getName().equals("New name");
        assert customerService.getById(1L).getPhoneNumber().equals("+375285554556");
    }

    @Test
    public void testTopUpBalance() throws Exception {
        CustomerBalanceDto balanceDto = new CustomerBalanceDto();
        balanceDto.setMoney(500d);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/customers/top-up-balance/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(balanceDto))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
        assert (Math.abs(customerService.getById(1L).getBalance()) - 2000) <= 0.000001;
    }
}