package com.myfp.fp.dao;

import com.myfp.fp.entities.Service;

import java.util.List;

public interface ServiceDAO extends DAO<Service>{
    List<Service> readAll() throws DAOException;
    Service readByType(String str) throws DAOException;
}
