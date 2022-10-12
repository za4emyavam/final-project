package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.ServiceDAO;
import com.myfp.fp.entities.Service;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.ServiceService;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {
    ServiceDAO serviceDAO;

    public void setServiceDAO(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    @Override
    public List<Service> findAll() throws ServiceException {
        try {
            return serviceDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Service findByType(String type) throws ServiceException {
        try {
            return serviceDAO.readByType(type);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
