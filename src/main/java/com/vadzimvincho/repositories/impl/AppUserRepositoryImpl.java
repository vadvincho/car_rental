package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.AppUser;
import com.vadzimvincho.repositories.api.AppUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class AppUserRepositoryImpl extends GenericRepositoryImpl<AppUser> implements AppUserRepository {
    public AppUserRepositoryImpl() {
        setTClassName(AppUser.class);
    }

    @Override
    public AppUser findByLogin(String login) {
        Query query = entityManager.createQuery("SELECT u FROM AppUser as u WHERE u.login = : login");
        query.setParameter("login", login);
        List resultList = query.getResultList();
        return resultList.size() != 0 ? (AppUser) resultList.get(0) : null;
    }
}
