package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;

import java.util.List;

public class TariffServiceImpl implements TariffService {
    private TariffDAO tariffDAO;

    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }

    @Override
    public List<Tariff> findAll() throws ServiceException {
        try {
            return tariffDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tariff> findAll(int limit, int offset) throws ServiceException {
        try {
            return tariffDAO.readAll(limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tariff> findAll(int limit, int offset, String order) throws ServiceException {
        try {
            return tariffDAO.readAll(limit, offset, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Tariff> findAll(int limit, int offset, String orderBy, String order) throws ServiceException {
        try {
            return tariffDAO.readAll(limit, offset, orderBy, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long insertTariff(Tariff t) throws ServiceException {
        try {
            return tariffDAO.create(t);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try {
            tariffDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Tariff t) throws ServiceException {
        try {
            tariffDAO.update(t);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tariff findById(Long id) throws ServiceException {
        try {
            return tariffDAO.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer getNoOfRecords() throws ServiceException {
        try {
            return tariffDAO.getNoOfRecords();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
