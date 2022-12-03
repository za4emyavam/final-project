package com.myfp.fp.service;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.User;

import java.util.List;

public interface UserService {
    User findById(Long id) throws ServiceException;
    Long create(User entity) throws ServiceException;
    void update(User entity) throws ServiceException;
    User findByLoginAndPassword(String login, String password) throws ServiceException;
    List<User> findAll() throws ServiceException;
    List<User> findAll(int limit, int offset) throws ServiceException;
    Integer getNoOfRecords() throws ServiceException;

}
