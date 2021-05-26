package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.Manager;
import com.vadzimvincho.repositories.api.ManagerRepository;
import com.vadzimvincho.services.api.ManagerService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagerServiceImpl extends GenericServiceImpl<Manager, ManagerRepository> implements ManagerService {
    public ManagerServiceImpl(ManagerRepository managerRepository) {
        super(managerRepository, LoggerFactory.getLogger(ManagerServiceImpl.class));
    }
}
