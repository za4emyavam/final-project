package com.myfp.fp.dao;

import com.myfp.fp.entities.Entity;

public interface DAO<T extends Entity> {
    T read(Long id) throws DAOException;
    Long create(T entity) throws DAOException;
    void update(T entity) throws DAOException;
    void delete(Long id) throws DAOException;
}
