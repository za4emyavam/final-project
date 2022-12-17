package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void update(User entity) throws ServiceException {
        try {
            userDAO.update(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findById(Long id) throws ServiceException {
        try {
            return userDAO.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long create(User entity) throws ServiceException {
        try {
            return userDAO.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            return userDAO.readByLoginAndPassword(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll(int limit, int offset) throws ServiceException {
        try {
            return userDAO.readAll(limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer getNoOfRecords() throws ServiceException {
        try {
            return userDAO.getNoOfRecords();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int isUserExist(String login) throws ServiceException {
        try {
            return userDAO.isUserExist(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
