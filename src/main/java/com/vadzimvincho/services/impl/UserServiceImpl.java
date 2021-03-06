package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.models.entity.Customer;
import com.vadzimvincho.models.entity.Role;
import com.vadzimvincho.repositories.api.AppUserRepository;
import com.vadzimvincho.repositories.api.RoleRepository;
import com.vadzimvincho.services.api.CustomerService;
import com.vadzimvincho.services.api.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<AppUser, AppUserRepository> implements UserService {

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private CustomerService customerService;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CustomerService customerService) {
        super(appUserRepository, LoggerFactory.getLogger(UserService.class));
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }

    @Override
    public void saveUser(AppUser appUser) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        appUser.setRole(userRole);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        ownRepository.add(appUser);
        Customer customer = new Customer();
        customer.setUser(appUser);
        customer.setName(appUser.getLogin());
        customer.setBalance(0d);
        customerService.add(customer);
    }

    @Override
    public AppUser findByLogin(String login) {
        return ownRepository.findByLogin(login);
    }

    @Override
    public AppUser findByLoginAndPassword(String login, String password) {
        AppUser appUser = findByLogin(login);
        if (appUser != null && passwordEncoder.matches(password, appUser.getPassword())) {
            return appUser;
        } else throw new UsernameNotFoundException("Login or password incorrect");
    }
}
