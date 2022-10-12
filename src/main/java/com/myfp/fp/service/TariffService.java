package com.myfp.fp.service;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.Tariff;

import java.util.List;

public interface TariffService {
    List<Tariff> findAll() throws ServiceException;
    Long insertTariff(Tariff t) throws ServiceException;
    void deleteById(Long id) throws ServiceException;
    void update(Tariff t) throws ServiceException;
    Tariff findById(Long id) throws ServiceException;

}
