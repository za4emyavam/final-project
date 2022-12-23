package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.entities.User;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/**
 * A service for managing users in the database.
 *
 * <p>This service provides methods for creating, updating, and retrieving users, as well as for
 * hashing passwords and checking for the existence of users. It also implements the {@link UserService} interface,
 * which defines the methods that must be implemented by a user service.
 *
 * <p>The service uses a {@link UserDAO} to interact with the database, which can be injected via the
 * {@link #setUserDAO(UserDAO)} method.
 *
 * @see com.myfp.fp.dao.postgres.UserDAOPstSQL
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    /**
     * Sets the {@link UserDAO} that this service will use to interact with the database.
     *
     * @param userDAO the {@link UserDAO} to set
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param entity the {@link User} to update
     * @throws ServiceException if an error occurs while updating the user
     */
    @Override
    public void update(User entity) throws ServiceException {
        try {
            userDAO.update(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link User} with the specified ID, or null if no such user exists
     * @throws ServiceException if an error occurs while retrieving the user
     */
    @Override
    public User findById(Long id) throws ServiceException {
        try {
            return userDAO.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Hashes the password of the given user entity and creates and add it in the database.
     *
     * @param entity the user entity to create
     * @return the generated id of the new user
     * @throws ServiceException if an error occurs while creating the user or hashing the password
     */
    @Override
    public Long create(User entity) throws ServiceException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(entity.getPass().getBytes());
            entity.setPass(Base64.getEncoder().encodeToString(hash));
            return userDAO.create(entity);
        } catch (DAOException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * This method retrieves a user from the database by their login and password.
     * The password is passed through the SHA-256 hash function and encoded with Base64 before being used to query the database.
     *
     * @param login    The login of the user to be retrieved
     * @param password The password of the user to be retrieved
     * @return A {@link User} object representing the user with the specified login and password
     * @throws ServiceException If there is an error while interacting with the database or hashing the password
     */
    @Override
    public User findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            String hashPass = Base64.getEncoder().encodeToString(hash);
            return userDAO.readByLoginAndPassword(login, hashPass);
        } catch (DAOException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a list of all users in the database.
     *
     * @return a list of all users in the database
     * @throws ServiceException if an error occurs while retrieving the users
     */
    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a list of users from the database, starting from a specific offset and limited
     * by a given number of records.
     *
     * @param limit  the maximum number of users to retrieve
     * @param offset the number of users to skip before retrieving the list
     * @return a list of users from the database, starting from the specified offset and limited
     * by the given number of records
     * @throws ServiceException if an error occurs while retrieving the users
     */
    @Override
    public List<User> findAll(int limit, int offset) throws ServiceException {
        try {
            return userDAO.readAll(limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves the total number of users in the database.
     *
     * @return the total number of users in the database
     * @throws ServiceException if an error occurs while retrieving the number of users
     */
    @Override
    public Integer getNoOfRecords() throws ServiceException {
        try {
            return userDAO.getNoOfRecords();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Checks if a user with the specified login exists in the database.
     *
     * @param login the login of the user to check
     * @return 1 if a user with the specified login exists, 0 otherwise
     * @throws ServiceException if an error occurs while checking for the user
     */
    @Override
    public int isUserExist(String login) throws ServiceException {
        try {
            return userDAO.isUserExist(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
