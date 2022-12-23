package com.myfp.fp.service;

import com.myfp.fp.entities.Transaction;

import java.util.List;

public interface TransactionService {
    Long insertTransaction(Transaction transaction) throws ServiceException;
    List<Transaction> readAllTransactionsByUserID(Long id) throws ServiceException;
}
