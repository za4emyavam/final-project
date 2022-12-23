package com.myfp.fp.service;

import com.myfp.fp.entities.User;

import java.util.List;

/**
 * An interface for interacting with the database to perform CRUD (create, read, update, delete) operations on user entities.
 */
public interface UserService {
    /**
     * Retrieves a user with the specified ID from the database.
     *
     * @param id the ID of the user to retrieve
     * @return a {@link User} object representing the user with the specified ID
     * @throws ServiceException if an error occurs while retrieving the user
     */
    User findById(Long id) throws ServiceException;
    /**
     * Creates and adds a new user to the database.
     *
     * @param entity the user entity to create
     * @return the generated ID of the new user
     * @throws ServiceException if an error occurs while creating the user
     */
    Long create(User entity) throws ServiceException;
    /**
     * Updates an existing user in the database.
     *
     * @param entity the user entity to update
     * @throws ServiceException if an error occurs while updating the user
     */
    void update(User entity) throws ServiceException;
    /**
     * Retrieves a user with the specified login and password from the database.
     *
     * @param login the login of the user to retrieve
     * @param password the password of the user to retrieve
     * @return a {@link User} object representing the user with the specified login and password
     * @throws ServiceException if an error occurs while retrieving the user
     */
    User findByLoginAndPassword(String login, String password) throws ServiceException;
    /**
     * Checks if a user with the specified login exists in the database.
     *
     * @param login the login of the user to check
     * @return 1 if a user with the specified login exists, 0 otherwise
     * @throws ServiceException if an error occurs while interacting with the database
     */
    int isUserExist(String login) throws ServiceException;
    /**
     * Retrieves a list of all users from the database.
     *
     * @return a list of {@link User} objects representing all users in the database
     * @throws ServiceException if an error occurs while interacting with the database
     */
    List<User> findAll() throws ServiceException;
    /**
     * Retrieves a list of users from the database with pagination.
     *
     * @param limit the maximum number of users to retrieve
     * @param offset the number of users to skip before retrieving the list
     * @return a list of {@link User} objects representing the specified number of users starting from the specified offset
     * @throws ServiceException if an error occurs while interacting with the database
     */
    List<User> findAll(int limit, int offset) throws ServiceException;
    /**
     * Retrieves the total number of users in the database.
     *
     * @return the total number of users in the database
     * @throws ServiceException if an error occurs while interacting with the database
     */
    Integer getNoOfRecords() throws ServiceException;

}
