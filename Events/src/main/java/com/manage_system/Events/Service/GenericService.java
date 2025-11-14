package com.manage_system.Events.Service;

import java.util.List;
import java.util.Optional;

public interface GenericService <T, ID> {
    T create(T entity);
    List<T> getAll();
    Optional<T> getById(ID id);
    boolean delete(ID id);
    T update(ID id, T entity);
}
