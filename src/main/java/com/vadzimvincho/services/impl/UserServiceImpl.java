package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.models.entity.Role;
import com.vadzimvincho.repositories.api.AppUserRepository;
import com.vadzimvincho.repositories.api.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private AppUserRepository appUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(AppUser appUser) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        appUser.setRole(userRole);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepository.add(appUser);
    }

    public AppUser findByLogin(String login) {
        return appUserRepository.findByLogin(login);
    }

    public AppUser findByLoginAndPassword(String login, String password) {
        AppUser appUser = findByLogin(login);
        if (appUser != null && passwordEncoder.matches(password, appUser.getPassword())) {
            return appUser;
        } else throw new UsernameNotFoundException("Login or password incorrect");
    }
}
