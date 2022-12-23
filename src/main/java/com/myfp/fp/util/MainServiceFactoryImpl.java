package com.myfp.fp.util;

import com.myfp.fp.dao.*;
import com.myfp.fp.dao.postgres.*;
import com.myfp.fp.service.*;
import com.myfp.fp.service.impl.*;

/**

 MainServiceFactoryImpl is a class that implements the {@link ServiceFactory} interface and provides implementations

 for the various service and DAO objects.

 <p>It follows the Singleton design pattern, where the {@link #getInstance()} method is used to get the single
 instance of the class. This instance can then be used to get the various service and DAO objects through the

 methods provided by the {@link ServiceFactory} interface.

 <p>The service objects returned by this factory are initialized with their respective DAO objects, which are also
 obtained through this factory. This allows for a centralized location for creating and managing these objects,

 and makes it easier to switch out the implementations if needed.

 */
public class MainServiceFactoryImpl implements ServiceFactory {
    private static MainServiceFactoryImpl mainServiceFactory;

    /**

     Returns the single instance of this class. If the instance has not been created yet, it will be created and
     returned. Otherwise, the existing instance will be returned.
     @return the single instance of this class
     */
    public static MainServiceFactoryImpl getInstance() {
        if(mainServiceFactory == null) {
            mainServiceFactory = new MainServiceFactoryImpl();
        }
        return mainServiceFactory;
    }

    /**

     Returns an implementation of the {@link UserService} interface. The service object is initialized with
     the {@link UserDAO} object obtained through this factory.
     @return an implementation of the {@link UserService} interface
     @throws FactoryException if there is an error creating the service object
     */
    @Override
    public UserService getUserService() throws FactoryException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(getUserDAO());
        return userService;
    }

    /**

     Returns an implementation of the {@link UserDAO} interface.
     @return an implementation of the {@link UserDAO} interface
     @throws FactoryException if there is an error creating the DAO object
     */
    @Override
    public UserDAO getUserDAO() throws FactoryException {
        UserDAO userDAO = new UserDAOPstSQL();
        return userDAO;
    }

    /**

     Returns an implementation of the {@link TariffService} interface. The service object is initialized with
     the {@link TariffDAO} object obtained through this factory.
     @return an implementation of the {@link TariffService} interface
     @throws FactoryException if there is an error creating the service object
     */
    @Override
    public TariffService getTariffService() throws FactoryException {
        TariffServiceImpl tariffService = new TariffServiceImpl();
        tariffService.setTariffDAO(getTariffDAO());
        return tariffService;
    }

    /**

     Returns an implementation of the {@link TariffDAO} interface.
     @return an implementation of the {@link TariffDAO} interface
     @throws FactoryException if there is an error creating the DAO object
     */
    @Override
    public TariffDAO getTariffDAO() throws FactoryException {
        TariffDAO tariffDAO = new TariffDAOPstSQL();
        return tariffDAO;
    }

    /**

     Returns an implementation of the {@link ServiceService} interface. The service object is initialized with
     the {@link ServiceDAO} object obtained through this factory.
     @return an implementation of the {@link ServiceService} interface
     @throws FactoryException if there is an error creating the service object
     */
    @Override
    public ServiceService getServiceService() throws FactoryException {
        ServiceServiceImpl serviceService = new ServiceServiceImpl();
        serviceService.setServiceDAO(getServiceDAO());
        return serviceService;
    }

    /**

     Returns an implementation of the {@link ServiceDAO} interface.
     @return an implementation of the {@link ServiceDAO} interface
     @throws FactoryException if there is an error creating the DAO object
     */
    @Override
    public ServiceDAO getServiceDAO() throws FactoryException {
        ServiceDAO serviceDAO = new ServiceDAOPstSQL();
        return serviceDAO;
    }

    /**

     Returns an implementation of the {@link TransactionService} interface. The service object is initialized with
     the {@link TransactionDAO} object obtained through this factory.
     @return an implementation of the {@link TransactionService} interface
     @throws FactoryException if there is an error creating the service object
     */
    @Override
    public TransactionService getTransactionService() throws FactoryException {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        transactionService.setTransactionDAO(getTransactionDAO());
        return transactionService;
    }

    /**

     Returns an implementation of the {@link TransactionDAO} interface.
     @return an implementation of the {@link TransactionDAO} interface
     @throws FactoryException if there is an error creating the DAO object
     */
    @Override
    public TransactionDAO getTransactionDAO() throws FactoryException {
        TransactionDAO transactionDAO = new TransactionDAOPstSQL();
        return transactionDAO;
    }

    /**

     Returns an implementation of the {@link ConnectionRequestService} interface. The service object is initialized with
     the {@link ConnectionRequestDAO} object obtained through this factory.
     @return an implementation of the {@link ConnectionRequestService} interface
     @throws FactoryException if there is an error creating the service object
     */
    @Override
    public ConnectionRequestService getConnectionRequestService() throws FactoryException {
        ConnectionRequestServiceImpl connectionRequestService = new ConnectionRequestServiceImpl();
        connectionRequestService.setConnectionRequestDAO(getConnectionRequestDAO());
        return connectionRequestService;
    }

    /**

     Returns an implementation of the {@link ConnectionRequestDAO} interface.
     @return an implementation of the {@link ConnectionRequestDAO} interface
     @throws FactoryException if there is an error creating the DAO object
     */
    @Override
    public ConnectionRequestDAO getConnectionRequestDAO() throws FactoryException {
        ConnectionRequestDAO connectionRequestDAO = new ConnectionRequestDAOPstSQL();
        return connectionRequestDAO;
    }

    /**

     Returns an implementation of the {@link UserTariffsService} interface. The service object is initialized with
     the {@link UserTariffsDAO} object obtained through this factory.
     @return an implementation of the {@link UserTariffsService} interface
     @throws FactoryException if there is an error creating the service object
     */
    @Override
    public UserTariffsService getUserTariffsService() throws FactoryException {
        UserTariffsServiceImpl userTariffsService = new UserTariffsServiceImpl();
        userTariffsService.setUserTariffsDAO(getUserTariffsDAO());
        return userTariffsService;
    }

    /**

     Returns an implementation of the {@link UserTariffsDAO} interface.
     @return an implementation of the {@link UserTariffsDAO} interface
     @throws FactoryException if there is an error creating the DAO object
     */
    @Override
    public UserTariffsDAO getUserTariffsDAO() throws FactoryException {
        UserTariffsDAO userTariffsDAO = new UserTariffsDAOPstSQL();
        return userTariffsDAO;
    }

    /**

     Returns an implementation of the {@link CheckService} interface. The service object is initialized with
     the {@link CheckDAO} object obtained through this factory.
     @return an implementation of the {@link CheckService} interface
     @throws FactoryException if there is an error creating the service object
     */
    @Override
    public CheckService getCheckService() throws FactoryException {
        CheckServiceImpl checkService = new CheckServiceImpl();
        checkService.setCheckDAO(getCheckDAO());
        return checkService;
    }

    /**

     Returns an implementation of the {@link CheckDAO} interface.
     @return an implementation of the {@link CheckDAO} interface
     @throws FactoryException if there is an error creating the DAO object
     */
    @Override
    public CheckDAO getCheckDAO() throws FactoryException {
        CheckDAO checkDAO = new CheckDAOPstSQL();
        return checkDAO;
    }
}
