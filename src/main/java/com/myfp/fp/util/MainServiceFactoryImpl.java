package com.myfp.fp.util;

import com.myfp.fp.dao.*;
import com.myfp.fp.dao.postgres.*;
import com.myfp.fp.service.*;
import com.myfp.fp.service.impl.*;

public class MainServiceFactoryImpl implements ServiceFactory {
    private static MainServiceFactoryImpl mainServiceFactory;

    public static MainServiceFactoryImpl getInstance() {
        if(mainServiceFactory == null) {
            mainServiceFactory = new MainServiceFactoryImpl();
        }
        return mainServiceFactory;
    }

    @Override
    public UserService getUserService() throws FactoryException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(getUserDAO());
        return userService;
    }

    @Override
    public UserDAO getUserDAO() throws FactoryException {
        UserDAOPstSQL userDAO = new UserDAOPstSQL();
        return userDAO;
    }

    @Override
    public TariffService getTariffService() throws FactoryException {
        TariffServiceImpl tariffService = new TariffServiceImpl();
        tariffService.setTariffDAO(getTariffDAO());
        return tariffService;
    }

    @Override
    public TariffDAO getTariffDAO() throws FactoryException {
        TariffDAOPstSQL tariffDAO = new TariffDAOPstSQL();
        return tariffDAO;
    }

    @Override
    public ServiceService getServiceService() throws FactoryException {
        ServiceServiceImpl serviceService = new ServiceServiceImpl();
        serviceService.setServiceDAO(getServiceDAO());
        return serviceService;
    }

    @Override
    public ServiceDAO getServiceDAO() throws FactoryException {
        ServiceDAOPstSQL serviceDAO = new ServiceDAOPstSQL();
        return serviceDAO;
    }

    @Override
    public TransactionService getTransactionService() throws FactoryException {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        transactionService.setTransactionDAO(getTransactionDAO());
        return transactionService;
    }

    @Override
    public TransactionDAO getTransactionDAO() throws FactoryException {
        TransactionDAOPstSQL transactionDAO = new TransactionDAOPstSQL();
        return transactionDAO;
    }

    @Override
    public ConnectionRequestService getConnectionRequestService() throws FactoryException {
        ConnectionRequestServiceImpl connectionRequestService = new ConnectionRequestServiceImpl();
        connectionRequestService.setConnectionRequestDAO(getConnectionRequestDAO());
        return connectionRequestService;
    }

    @Override
    public ConnectionRequestDAO getConnectionRequestDAO() throws FactoryException {
        ConnectionRequestDAOPstSQL connectionRequestDAO = new ConnectionRequestDAOPstSQL();
        return connectionRequestDAO;
    }

    @Override
    public UserTariffsService getUserTariffsService() throws FactoryException {
        UserTariffsServiceImpl userTariffsService = new UserTariffsServiceImpl();
        userTariffsService.setUserTariffsDAO(getUserTariffsDAO());
        return userTariffsService;
    }

    @Override
    public UserTariffsDAO getUserTariffsDAO() throws FactoryException {
        UserTariffsDAOPstSQL userTariffsDAO = new UserTariffsDAOPstSQL();
        return userTariffsDAO;
    }
}
