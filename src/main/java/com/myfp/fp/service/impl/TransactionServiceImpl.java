package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.dao.TransactionDAO;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.Transaction;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.service.TransactionService;

import java.util.List;

/**
 * TransactionServiceImpl is an implementation of the {@link TransactionService} interface. It provides
 * <p>
 * methods for interacting with the database to perform operations related to transactions.
 *
 * <p>The service object is initialized with a {@link TransactionDAO} object, which is used to access the
 * database. This allows for a separation of concerns between the service layer and the data access layer.
 */
public class TransactionServiceImpl implements TransactionService {
    private TransactionDAO transactionDAO;

    /**
     * Sets the {@link TransactionDAO} object to be used by this service object.
     *
     * @param transactionDAO the {@link TransactionDAO} object to be used by this service object
     */
    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     * Inserts a new transaction into the database.
     *
     * @param transaction the transaction to insert
     * @return the ID of the inserted transaction
     * @throws ServiceException if an error occurs while inserting the transaction
     */
    @Override
    public Long insertTransaction(Transaction transaction) throws ServiceException {
        try {
            return transactionDAO.create(transaction);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a list of all transactions for a specific user.
     *
     * @param id the ID of the user
     * @return a list of {@link Transaction} for the specified user
     * @throws ServiceException if an error occurs while retrieving the transactions
     */
    @Override
    public List<Transaction> readAllTransactionsByUserID(Long id) throws ServiceException {
        try {
            return transactionDAO.readAllByUserBalanceId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
