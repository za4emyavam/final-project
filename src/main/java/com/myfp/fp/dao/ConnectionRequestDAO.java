package com.myfp.fp.dao;

import com.myfp.fp.entities.ConnectionRequest;

import java.util.List;

public interface ConnectionRequestDAO extends DAO<ConnectionRequest>{
    List<ConnectionRequest> readAll() throws DAOException;
}
