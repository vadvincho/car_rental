package com.vadzimvincho.repositories.api;

import java.util.List;

public interface GenericRepository<T> {

    Long add(T object);

    void remove(T object);

    T getById(Long id);

    void update(T object);

    List<T> getAll();
}
