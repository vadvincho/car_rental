package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.Role;
import com.vadzimvincho.repositories.api.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Role> implements RoleRepository {
    public RoleRepositoryImpl() {
        setTClassName(Role.class);
    }

    @Override
    public Role findByName(String name) {
//        Query query = entityManager.createQuery("SELECT r FROM Role as r WHERE r.name = :roleName");
//        query.setParameter("roleName", name);
//        List resultList = query.getResultList();
//        return resultList.size() != 0 ? (Role) resultList.get(0) : null;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);
        query.select(root).where(criteriaBuilder.equal(root.get("name"), name));
        return entityManager.createQuery(query).getSingleResult();
    }
}
