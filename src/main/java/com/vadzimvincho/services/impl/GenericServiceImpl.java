package com.vadzimvincho.services.impl;

import com.vadzimvincho.models.entity.BaseEntity;
import com.vadzimvincho.repositories.api.GenericRepository;
import com.vadzimvincho.exceptions.DaoException;
import com.vadzimvincho.services.api.GenericService;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

@Transactional
public class GenericServiceImpl<T extends BaseEntity, R extends GenericRepository> implements GenericService<T> {

    protected final R ownRepository;
    protected final Logger logger;

    public GenericServiceImpl(R ownRepository, Logger logger) {
        this.ownRepository = ownRepository;
        this.logger = logger;
    }

    @Override
    public Long add(T t) throws DaoException {
        Long id;
        if (t == null) {
            String message = "Object is null";
            logger.warn(message);
            throw new DaoException(message);
        }
        id = ownRepository.add(t);
        return id;
    }

    @Override
    public void remove(T t) throws DaoException {
        if (t == null) {
            String message = "Object is null";
            logger.warn(message);
            throw new DaoException(message);
        }
        ownRepository.remove(t);
    }

    @Override
    public List<T> getAll() throws DaoException {
        List<T> list;
        list = ownRepository.getAll();
        if (list.isEmpty()) {
            String message = "The list of objects is empty";
            logger.warn(message);
            throw new DaoException(message);
        }
        return list;
    }

    @Override
    public T getById(Long id) throws DaoException {
        T t;
        t = (T) ownRepository.getById(id);
        if (t == null) {
            String message = "No such object with this ID";
            logger.warn(message);
            throw new DaoException(message);
        }
        return t;
    }

    @Override
    public void update(T tPatch) throws DaoException {
        if (tPatch == null) {
            String message = "Object is null";
            logger.warn(message);
            throw new DaoException(message);
        }
        try {
            T tPatched = patch(tPatch);
            ownRepository.update(tPatched);
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private T patch(T tPatch) throws DaoException {
        T t = (T) ownRepository.getById(tPatch.getId());
        ReflectionUtils.doWithFields(tPatch.getClass(), tPatchField -> {
            ReflectionUtils.makeAccessible(tPatchField);
            if (tPatchField.get(tPatch) != null) {
                Field tField = ReflectionUtils.findField(t.getClass(), tPatchField.getName());
                ReflectionUtils.makeAccessible(tField);
                ReflectionUtils.setField(tField, t, tPatchField.get(tPatch));
            }
        });
        return t;
    }
}