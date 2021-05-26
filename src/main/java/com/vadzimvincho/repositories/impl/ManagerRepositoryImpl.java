package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.Manager;
import com.vadzimvincho.repositories.api.ManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepositoryImpl extends GenericRepositoryImpl<Manager> implements ManagerRepository {
    public ManagerRepositoryImpl() {
        setTClassName(Manager.class);
    }
}
