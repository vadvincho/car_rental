package com.vadzimvincho.controllers.rest;

import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.exceptions.Message;
import com.vadzimvincho.models.dto.AppUserDto;
import com.vadzimvincho.models.entity.AppUser;
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
@RequestMapping(value = "/users", produces = "application/json")
public class UserControllerRest {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final static Logger logger = LoggerFactory.getLogger(UserControllerRest.class);

    @Autowired
    public UserControllerRest(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody AppUserDto appUserDto) throws DaoException {
        if (userService.findByLogin(appUserDto.getLogin()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new Message("Error: User with such login is exist"));
        } else {
            userService.saveUser(modelMapper.map(appUserDto, AppUser.class));
            return ResponseEntity.ok(new Message("User " + appUserDto.getLogin() + " created"));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity remove(@PathVariable("id") Long id) throws DaoException {
        AppUser appUser = userService.getById(id);
        userService.remove(appUser);
        return ResponseEntity.ok("User " + appUser.getLogin() + " deleted");
    }

    @GetMapping
    public List<AppUserDto> getAll() throws DaoException {
        return getAppUserDto(userService.getAll());
    }

    @GetMapping(value = "/{id}")
    public AppUserDto getById(@PathVariable("id") Long id) throws DaoException {
        return modelMapper.map(userService.getById((id)), AppUserDto.class);
    }

    @GetMapping(value = "/bylogin/{login}")
    public AppUserDto findByLogin(@PathVariable("login") String login) throws DaoException {
        return modelMapper.map(userService.findByLogin((login)), AppUserDto.class);
    }

    @PatchMapping
    public ResponseEntity update(@RequestBody AppUserDto appUserDto) throws DaoException {
        userService.update(modelMapper.map(appUserDto, AppUser.class));
        return ResponseEntity.ok(new Message("User " + appUserDto.getLogin() + " updated"));
    }

    private List<AppUserDto> getAppUserDto(List<AppUser> allUsers) {
        return allUsers.stream()
                .map(x -> modelMapper.map(x, AppUserDto.class))
                .collect(Collectors.toList());
    }
}
