package com.myfp.fp.service.impl;

import com.myfp.fp.dao.CheckDAO;
import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.Check;
import com.myfp.fp.service.CheckService;
import com.myfp.fp.service.ServiceException;

public class CheckServiceImpl implements CheckService {
    private CheckDAO checkDAO;

    public void setCheckDAO(CheckDAO checkDAO) {
        this.checkDAO = checkDAO;
    }

    @Override
    public Check readLast() throws ServiceException {
        try {
            return checkDAO.readLast();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
