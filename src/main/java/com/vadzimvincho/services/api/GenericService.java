package com.vadzimvincho.services.api;

import com.vadzimvincho.exceptions.DaoException;

import java.util.List;

public interface GenericService<T> {

    Long add(T object) throws DaoException;

    T getById(Long id) throws DaoException;

    void update(T object) throws DaoException;

    void remove(T object) throws DaoException;

    List<T> getAll() throws DaoException;
}
