package com.myfp.fp.service;

import com.myfp.fp.entities.Tariff;

import java.util.List;

public interface TariffService {
    List<Tariff> findAll() throws ServiceException;
    List<Tariff> findAll(int limit, int offset) throws ServiceException;
    List<Tariff> findAll(int limit, int offset, String order) throws ServiceException;
    List<Tariff> findAll(int limit, int offset, String orderBy, String order) throws ServiceException;
    Long insertTariff(Tariff t) throws ServiceException;
    void deleteById(Long id) throws ServiceException;
    void update(Tariff t) throws ServiceException;
    Tariff findById(Long id) throws ServiceException;
    Integer getNoOfRecords() throws ServiceException;

}
