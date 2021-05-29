package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.models.dto.CustomerBalanceDto;
import com.vadzimvincho.models.dto.CustomerPayForOrderDto;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.models.dto.CustomerDto;
import com.vadzimvincho.services.api.CustomerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerControllerRest {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;
    private final static Logger logger = LoggerFactory.getLogger(CustomerControllerRest.class);

    @Autowired
    public CustomerControllerRest(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody CustomerDto customerDto) throws DaoException {
        customerService.add(modelMapper.map(customerDto, Customer.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) throws DaoException {
        customerService.remove(customerService.getById(id));
        return ResponseEntity.ok(HttpStatus.OK);
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
    public ResponseEntity<HttpStatus> update(@RequestBody CustomerDto customerDto) throws DaoException {
        customerService.update(modelMapper.map(customerDto, Customer.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value="/top-up-balance")
    public ResponseEntity<HttpStatus> topUpBalance(@RequestBody CustomerBalanceDto money) throws DaoException {
        customerService.topUpBalance(money);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private List<CustomerDto> getCustomerDto(List<Customer> allCustomers) {
        return allCustomers.stream()
                .map(x -> modelMapper.map(x, CustomerDto.class))
                .collect(Collectors.toList());
    }
}
