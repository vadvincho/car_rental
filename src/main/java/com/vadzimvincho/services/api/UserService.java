package com.vadzimvincho.services.api;

import com.vadzimvincho.models.entity.AppUser;

public interface UserService extends GenericService<AppUser>{
    void saveUser(AppUser appUser);

    AppUser findByLogin(String login);

    AppUser findByLoginAndPassword(String login, String password);
}
