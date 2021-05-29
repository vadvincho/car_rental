package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.models.dto.CarModelDto;
import com.vadzimvincho.models.entity.CarModel;

import com.vadzimvincho.services.api.CarModelService;
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
@RequestMapping(value = "/car-models", produces = "application/json")
public class CarModelControllerRest {
    private final CarModelService carModelService;
    private final ModelMapper modelMapper;
    private final static Logger logger = LoggerFactory.getLogger(CarModelControllerRest.class);

    @Autowired
    public CarModelControllerRest(CarModelService carModelService, ModelMapper modelMapper) {
        this.carModelService = carModelService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody CarModelDto carModelDto) throws DaoException {
        carModelService.add(modelMapper.map(carModelDto, CarModel.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) throws DaoException {
        carModelService.remove(carModelService.getById(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<CarModelDto> getAll() throws DaoException {
        return getCarModelDto(carModelService.getAll());
    }

    @GetMapping(value = "/{id}")
    public CarModelDto getById(@PathVariable("id") Long id) throws DaoException {
        return modelMapper.map(carModelService.getById((id)), CarModelDto.class);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody CarModelDto carModelDto) throws DaoException {
        carModelService.update(modelMapper.map(carModelDto, CarModel.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/make/{id}")
    public List<CarModelDto> getByModel(@PathVariable("id") Long id) throws DaoException {
        return getCarModelDto(carModelService.getByCarMake(id));
    }

    private List<CarModelDto> getCarModelDto(List<CarModel> allCarModels) {
        return allCarModels.stream()
                .map(x -> modelMapper.map(x, CarModelDto.class))
                .collect(Collectors.toList());
    }
}
