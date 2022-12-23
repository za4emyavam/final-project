package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.dao.UserTariffsDAO;
import com.myfp.fp.entities.UserTariffs;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserTariffsService;

import java.util.List;

/**
 * A service for managing user tariffs in the database.
 *
 * <p>This service provides methods for retrieving, deleting and updating user tariffs, and for getting a list of all user tariffs ids.
 * It also implements the {@link UserTariffsService} interface, which defines the methods that must be implemented by a user tariffs service.
 *
 * <p>The service uses a {@link UserTariffsDAO} to interact with the database, which can be injected via the
 * {@link #setUserTariffsDAO(UserTariffsDAO)} method.
 *
 * @see com.myfp.fp.dao.postgres.UserTariffsDAOPstSQL
 */
public class UserTariffsServiceImpl implements UserTariffsService {

    private UserTariffsDAO userTariffsDAO;

    /**
     * Sets the {@link UserTariffsDAO} that this service will use to interact with the database.
     *
     * @param userTariffsDAO the {@link UserTariffsDAO} to set
     */
    public void setUserTariffsDAO(UserTariffsDAO userTariffsDAO) {
        this.userTariffsDAO = userTariffsDAO;
    }

    /**
     * Retrieves a list of user tariffs for a specific user.
     *
     * @param id the ID of the user to retrieve the tariffs for
     * @return a list of {@link UserTariffs} for the specified user
     * @throws ServiceException if an error occurs while retrieving the user tariffs
     */
    @Override
    public List<UserTariffs> readAllByUserId(Long id) throws ServiceException {
        try {
            return userTariffsDAO.readAllByUser(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a list of all user tariffs ids from the database.
     *
     * @return a list of all user tariffs ids
     * @throws ServiceException if an error occurs while retrieving the user tariffs ids
     */
    @Override
    public List<Integer> getAllId() throws ServiceException {
        try {
            return userTariffsDAO.getAllId();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Checks each user who is subscribed to the rates whether the frequencyOfPayment has passed since the last payment.
     * If yes, if possible, debits funds from the account or changes the user's status to blocked.
     *
     * @param userId the user id of the person requesting the payment status of all users
     * @return the number of users from whom funds were debited and the amount of funds debited
     * @throws ServiceException if an error occurs while checking the payment
     */
    @Override
    public List<Integer> checkPaymentOfAllUsers(Long userId) throws ServiceException {
        try {
            return userTariffsDAO.checkPaymentOfAllUsers(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes a user tariff with the specified user and tariff IDs from the database.
     *
     * @param userId the ID of the user for the user tariff to delete
     * @param tariffId the ID of the tariff for the user tariff to delete
     * @throws ServiceException if an error occurs while deleting the user tariff
     */
    @Override
    public void deleteByUserIdTariffId(Long userId, Long tariffId) throws ServiceException {
        try {
            userTariffsDAO.deleteByUserIdTariffId(userId, tariffId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
