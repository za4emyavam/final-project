package com.myfp.fp.dao;

import com.myfp.fp.entities.Transaction;

import java.util.List;

public interface TransactionDAO extends DAO<Transaction> {
    List<Transaction> readAllByUserBalanceId(Long id) throws DAOException;
}
