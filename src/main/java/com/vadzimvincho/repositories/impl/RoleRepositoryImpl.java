package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.Role;
import com.vadzimvincho.repositories.api.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Role> implements RoleRepository {
    @Override
    public Role findByName(String name) {
        Query query = entityManager.createQuery("SELECT r FROM Role as r WHERE r.name = :roleName");
        query.setParameter("roleName",name);
        List resultList = query.getResultList();
        return resultList.size()!=0 ? (Role) resultList.get(0) : null;
    }
}
