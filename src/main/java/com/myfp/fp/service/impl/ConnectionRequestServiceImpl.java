package com.myfp.fp.service.impl;

import com.myfp.fp.dao.ConnectionRequestDAO;
import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;

import java.util.List;

public class ConnectionRequestServiceImpl implements ConnectionRequestService {
    private ConnectionRequestDAO connectionRequestDAO;

    public void setConnectionRequestDAO(ConnectionRequestDAO connectionRequestDAO) {
        this.connectionRequestDAO = connectionRequestDAO;
    }
    @Override
    public Long insertRequest(ConnectionRequest entity) throws ServiceException {
        try {
            return connectionRequestDAO.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ConnectionRequest> readAll() throws ServiceException {
        try {
            return connectionRequestDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ConnectionRequest> readAll(int limit, int offset) throws ServiceException {
        try {
            return connectionRequestDAO.readAll(limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ConnectionRequest read(Long id) throws ServiceException {
        try {
            return connectionRequestDAO.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(ConnectionRequest entity) throws ServiceException {
        try {
            connectionRequestDAO.update(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer getNoOfRecords() throws ServiceException {
        try {
            return connectionRequestDAO.getNoOfRecords();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
