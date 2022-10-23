package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TransactionDAO;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.Transaction;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private TransactionDAO transactionDAO;

    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public Long insertTransaction(Transaction transaction) throws ServiceException {
        try {
            return transactionDAO.create(transaction);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Transaction> readAllTransactionsByUserBalance(Long id) throws ServiceException {
        try {
            return transactionDAO.readAllByUserBalanceId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
