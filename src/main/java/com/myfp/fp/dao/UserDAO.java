package com.myfp.fp.dao;

import com.myfp.fp.entities.User;

import java.util.List;

public interface UserDAO extends DAO<User> {
    List<User> readAll() throws DAOException;

    User readByLogin(String login) throws DAOException;

    User readByLoginAndPassword(String login, String password) throws DAOException;

    void addNewUser(String email, String login, String password) throws DAOException;
}
