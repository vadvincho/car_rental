package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.Role;

public interface RoleRepository extends GenericRepository<Role> {
    Role findByName(String name);
}
