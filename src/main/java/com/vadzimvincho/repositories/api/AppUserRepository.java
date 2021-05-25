package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.AppUser;

public interface AppUserRepository extends GenericRepository<AppUser> {
    AppUser findByLogin(String login);
}
