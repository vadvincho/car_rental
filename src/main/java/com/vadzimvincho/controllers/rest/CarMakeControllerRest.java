package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.models.dto.CarMakeDto;
import com.vadzimvincho.models.entity.CarMake;

import com.vadzimvincho.services.api.CarMakeService;
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
@RequestMapping(value = "/car-makes", produces = "application/json")
public class CarMakeControllerRest {
    private final CarMakeService carMakeService;
    private final ModelMapper modelMapper;
    private final static Logger logger = LoggerFactory.getLogger(CarMakeControllerRest.class);

    @Autowired
    public CarMakeControllerRest(CarMakeService carMakeService, ModelMapper modelMapper) {
        this.carMakeService = carMakeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody CarMakeDto carMakeDto) throws DaoException {
        carMakeService.add(modelMapper.map(carMakeDto, CarMake.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) throws DaoException {
        carMakeService.remove(carMakeService.getById(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<CarMakeDto> getAll() throws DaoException {
        return getCarMakeDto(carMakeService.getAll());
    }

    @GetMapping(value = "/{id}")
    public CarMakeDto getById(@PathVariable("id") Long id) throws DaoException {
        return modelMapper.map(carMakeService.getById((id)), CarMakeDto.class);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody CarMakeDto carMakeDto) throws DaoException {
        carMakeService.update(modelMapper.map(carMakeDto, CarMake.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private List<CarMakeDto> getCarMakeDto(List<CarMake> allCarMakes) {
        return allCarMakes.stream()
                .map(x -> modelMapper.map(x, CarMakeDto.class))
                .collect(Collectors.toList());
    }
}
