package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.UserTariffsDAO;
import com.myfp.fp.entities.UserTariffs;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.UserTariffsService;

import java.util.List;

public class UserTariffsServiceImpl implements UserTariffsService {

    private UserTariffsDAO userTariffsDAO;

    public void setUserTariffsDAO(UserTariffsDAO userTariffsDAO) {
        this.userTariffsDAO = userTariffsDAO;
    }

    @Override
    public List<UserTariffs> readAllByUserId(Long id) throws ServiceException {
        try {
            return userTariffsDAO.readAllByUser(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Integer> getAllId() throws ServiceException {
        try {
            return userTariffsDAO.getAllId();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void checkPaymentOfAllUsers() throws ServiceException {
        try {
            userTariffsDAO.checkPaymentOfAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByUserIdTariffId(Long userId, Long tariffId) throws ServiceException {
        try {
            userTariffsDAO.deleteByUserIdTariffId(userId, tariffId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
