package com.myfp.fp.dao;

import com.myfp.fp.entities.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO extends DAO<User> {
    List<User> readAll() throws DAOException;
    List<User> readAll(int limit, int offset) throws DAOException;

    User readByLogin(String login) throws DAOException;

    User readByLoginAndPassword(String login, String password) throws DAOException;

    Integer getNoOfRecords() throws DAOException;
}
