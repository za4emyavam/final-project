package com.myfp.fp.util;

import com.myfp.fp.dao.*;
import com.myfp.fp.dao.postgres.*;
import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.service.*;
import com.myfp.fp.service.impl.*;

import java.sql.Connection;
import java.sql.SQLException;

public class MainServiceFactoryImpl implements ServiceFactory {
    private static MainServiceFactoryImpl mainServiceFactory;

    public static MainServiceFactoryImpl getInstance() {
        if(mainServiceFactory == null) {
            mainServiceFactory = new MainServiceFactoryImpl();
        }
        return mainServiceFactory;
    }
    private Connection connection;

    @Override
    public UserService getUserService() throws FactoryException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(getUserDAO());
        return userService;
    }

    @Override
    public UserDAO getUserDAO() throws FactoryException {
        /*UserDAOMySQL userDAO = new UserDAOMySQL();*/
        UserDAOPstSQL userDAO = new UserDAOPstSQL();
        userDAO.setConnection(getConnection());
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
        tariffDAO.setConnection(getConnection());
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
        serviceDAO.setConnection(getConnection());
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
        transactionDAO.setConnection(getConnection());
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
        connectionRequestDAO.setConnection(getConnection());
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
        userTariffsDAO.setConnection(getConnection());
        return userTariffsDAO;
    }

    @Override
    public Connection getConnection() throws FactoryException {
        if (connection == null) {
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (SQLException e) {
                throw new FactoryException(e);
            }
        }
        return connection;
    }

    @Override
    public void close() throws Exception{
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {}
    }
}
