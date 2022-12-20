package com.myfp.fp.dao;

import com.myfp.fp.entities.UserTariffs;

import java.util.List;

public interface UserTariffsDAO extends DAO<UserTariffs> {
    List<UserTariffs> readAll() throws DAOException;
    List<UserTariffs> readAllByUser(Long id) throws DAOException;
    List<Integer> getAllId() throws DAOException;
    void deleteByUserIdTariffId(Long userId, Long tariffId) throws DAOException;
    List<Integer> checkPaymentOfAllUsers(Long userId) throws DAOException;
}
