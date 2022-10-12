package com.myfp.fp.dao;

import com.myfp.fp.entities.Tariff;

import java.util.List;

public interface TariffDAO extends DAO<Tariff> {
    List<Tariff> readAll() throws DAOException;
}
