package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.exceptions.Message;
import com.vadzimvincho.models.dto.CustomerBalanceDto;
import com.vadzimvincho.models.dto.CustomerDto;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.services.api.CustomerService;
import com.vadzimvincho.services.api.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerControllerRest {
    private final CustomerService customerService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final static Logger logger = LoggerFactory.getLogger(CustomerControllerRest.class);

    @Autowired
    public CustomerControllerRest(CustomerService customerService, UserService userService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CustomerDto customerDto) throws DaoException {
        customerService.add(modelMapper.map(customerDto, Customer.class));
        return ResponseEntity.ok(new Message("Customer created"));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) throws DaoException {
        customerService.remove(customerService.getById(id));
        return ResponseEntity.ok(new Message("Customer deleted"));
    }

    @GetMapping
    public List<CustomerDto> getAll() throws DaoException {
        return getCustomerDto(customerService.getAll());
    }

    @GetMapping(value = "/{id}")
    public CustomerDto getById(@PathVariable("id") Long id) throws DaoException {
        return modelMapper.map(customerService.getById((id)), CustomerDto.class);
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody CustomerDto customerDto) throws DaoException {
        customerService.update(modelMapper.map(customerDto, Customer.class));
        return ResponseEntity.ok(new Message("Customer updated"));
    }

    @GetMapping(value = "/byUser/{id}")
    public CustomerDto getByUser(@PathVariable("id") Long id) throws DaoException {
        return modelMapper.map(customerService.getCustomerByUser(userService.getById(id)), CustomerDto.class);
    }

    @PostMapping(value = "/top-up-balance/{id}")
    public ResponseEntity<?> topUpBalance(@PathVariable("id") Long id, @RequestBody CustomerBalanceDto money) throws DaoException {
        customerService.topUpBalance(customerService.getById(id), money);
        return ResponseEntity.ok(new Message("Customer balance replenished"));
    }

    private List<CustomerDto> getCustomerDto(List<Customer> allCustomers) {
        return allCustomers.stream()
                .map(x -> modelMapper.map(x, CustomerDto.class))
                .collect(Collectors.toList());
    }
}
