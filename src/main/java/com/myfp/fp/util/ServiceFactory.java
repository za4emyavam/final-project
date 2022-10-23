package com.myfp.fp.util;

import com.myfp.fp.dao.*;
import com.myfp.fp.service.*;

import java.sql.Connection;

public interface ServiceFactory extends AutoCloseable {
    UserService getUserService() throws FactoryException;

    UserDAO getUserDAO() throws FactoryException;
    TariffService getTariffService() throws FactoryException;
    TariffDAO getTariffDAO() throws FactoryException;
    ServiceService getServiceService() throws FactoryException;
    ServiceDAO getServiceDAO() throws FactoryException;
    TransactionService getTransactionService() throws FactoryException;
    TransactionDAO getTransactionDAO() throws FactoryException;
    ConnectionRequestService getConnectionRequestService() throws FactoryException;
    ConnectionRequestDAO getConnectionRequestDAO() throws FactoryException;
    UserTariffsService getUserTariffsService() throws FactoryException;
    UserTariffsDAO getUserTariffsDAO() throws FactoryException;

    Connection getConnection() throws FactoryException;
}
