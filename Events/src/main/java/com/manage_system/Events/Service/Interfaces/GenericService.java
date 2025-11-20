package com.manage_system.Events.Service.Interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericService <T,I, ID> {
    T create(I entity);
    List<T> getAll();
    Optional<T> getById(ID id);
    boolean delete(ID id);
    T update(ID id, I entity);
}
