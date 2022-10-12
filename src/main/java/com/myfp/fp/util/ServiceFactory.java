package com.myfp.fp.util;

import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.dao.ServiceDAO;
import com.myfp.fp.service.ServiceService;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.service.UserService;

import java.sql.Connection;

public interface ServiceFactory extends AutoCloseable {
    UserService getUserService() throws FactoryException;

    UserDAO getUserDAO() throws FactoryException;
    TariffService getTariffService() throws FactoryException;
    TariffDAO getTariffDAO() throws FactoryException;
    ServiceService getServiceService() throws FactoryException;
    ServiceDAO getServiceDAO() throws FactoryException;
    Connection getConnection() throws FactoryException;
}
