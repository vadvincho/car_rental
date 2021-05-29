package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.exceptions.CarStatusException;
import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.exceptions.ResponseMessage;
import com.vadzimvincho.models.dto.OrderDto;
import com.vadzimvincho.models.entity.Order;
import com.vadzimvincho.services.api.CustomerService;
import com.vadzimvincho.services.api.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders", produces = "application/json")
public class OrderControllerRest {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final static Logger logger = LoggerFactory.getLogger(OrderControllerRest.class);

    @Autowired
    public OrderControllerRest(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody OrderDto orderDto) throws DaoException {
        orderService.create(modelMapper.map(orderDto, Order.class));
        return ResponseEntity.ok(new ResponseMessage("Order created"));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) throws DaoException {
        orderService.remove(orderService.getById(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<OrderDto> getAll() throws DaoException {
        return getOrderDto(orderService.getAll());
    }

    @GetMapping(value = "/{id}")
    public OrderDto getById(@PathVariable("id") Long id) throws DaoException {
        return modelMapper.map(orderService.getById((id)), OrderDto.class);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody OrderDto orderDto) throws DaoException {
        orderService.update(modelMapper.map(orderDto, Order.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }



//    @PostMapping(value="/pay-for-order")
//    public ResponseEntity<HttpStatus> payForOrder(@RequestBody Long orderId) throws DaoException {
//        orderService.payForOrder(orderId);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    private List<OrderDto> getOrderDto(List<Order> allOrders) {
        return allOrders.stream()
                .map(x -> modelMapper.map(x, OrderDto.class))
                .collect(Collectors.toList());
    }
}

