package com.myfp.fp.service;

import com.myfp.fp.entities.User;

import java.util.List;

public interface UserService {
    User findById(Long id) throws ServiceException;
    User findByLoginAndPassword(String login, String password) throws ServiceException;
    List<User> findAll() throws ServiceException;

    void addNewUser(String email, String login, String password) throws ServiceException;

}