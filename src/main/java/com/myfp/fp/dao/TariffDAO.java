package com.myfp.fp.dao;

import com.myfp.fp.entities.Tariff;

import java.util.List;

public interface TariffDAO extends DAO<Tariff> {
    List<Tariff> readAll() throws DAOException;
    List<Tariff> readAll(int limit, int offset) throws DAOException;
    Integer getNoOfRecords() throws DAOException;
}
