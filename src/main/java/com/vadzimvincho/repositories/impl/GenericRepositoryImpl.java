package com.vadzimvincho.repositories.impl;

import com.vadzimvincho.models.entity.BaseEntity;
import com.vadzimvincho.repositories.api.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GenericRepositoryImpl<T extends BaseEntity> implements GenericRepository<T> {

    private Class tClass;

    public void setTClassName(Class tClass) {
        this.tClass = tClass;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Long add(T object) {
        entityManager.persist(object);
        return object.getId();
    }

    @Override
    public T getById(Long id) {
        return (T) entityManager.find(tClass, id);
    }

    @Override
    public void update(T object) {
        entityManager.merge(object);
    }

    @Override
    public void remove(T object) {
        entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
