package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.models.entity.Car;
import com.vadzimvincho.models.entity.CarStatus;
import com.vadzimvincho.models.dto.CarDto;
import com.vadzimvincho.services.api.CarService;
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
@RequestMapping(value = "/cars", produces = "application/json")
public class CarControllerRest {
    private final CarService carService;
    private final ModelMapper modelMapper;
    private final static Logger logger = LoggerFactory.getLogger(CarControllerRest.class);

    @Autowired
    public CarControllerRest(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody CarDto carDto) throws DaoException {
        carService.add(modelMapper.map(carDto, Car.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Long id) throws DaoException {
        carService.remove(carService.getById(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<CarDto> getAll() throws DaoException {
        return getCarDto(carService.getAll());
    }

    @GetMapping(value = "/{id}")
    public CarDto getById(@PathVariable("id") Long id) throws DaoException {
        return modelMapper.map(carService.getById((id)), CarDto.class);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody CarDto carDto) throws DaoException {
        carService.update(modelMapper.map(carDto, Car.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/model/{id}")
    public List<CarDto> getByModel(@PathVariable("id") Long id) throws DaoException {
        return getCarDto(carService.getByModel(id));
    }

    @GetMapping(value = "/status")
    public List<CarDto> getByStatus(@RequestBody CarStatus status) {
        return getCarDto(carService.getByStatus(status.getStatus()));
    }

    @GetMapping(value = "/status-available")
    public List<CarDto> getAvailableCar() {
        return getCarDto(carService.getAvailableCar());
    }


    private List<CarDto> getCarDto(List<Car> allCars) {
        return allCars.stream()
                .map(x -> modelMapper.map(x, CarDto.class))
                .collect(Collectors.toList());
    }
}
