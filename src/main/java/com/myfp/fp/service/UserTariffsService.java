package com.myfp.fp.service;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.UserTariffs;

import java.util.List;

public interface UserTariffsService {
    List<UserTariffs> readAllByUserId(Long id) throws ServiceException;
    List<Integer> getAllId() throws ServiceException;
    void checkPaymentOfAllUsers() throws ServiceException;
}
