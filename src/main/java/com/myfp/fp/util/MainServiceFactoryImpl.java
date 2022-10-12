package com.myfp.fp.util;

import com.myfp.fp.dao.ServiceDAO;
import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.dao.UserDAO;
import com.myfp.fp.dao.mysql.ServiceDAOMySQL;
import com.myfp.fp.dao.mysql.TariffDAOMySQL;
import com.myfp.fp.dao.mysql.UserDAOMySQL;
import com.myfp.fp.service.ServiceService;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.service.UserService;
import com.myfp.fp.service.impl.ServiceServiceImpl;
import com.myfp.fp.service.impl.TariffServiceImpl;
import com.myfp.fp.service.impl.UserServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class MainServiceFactoryImpl implements ServiceFactory {
    private Connection connection;

    @Override
    public UserService getUserService() throws FactoryException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(getUserDAO());
        return userService;
    }

    @Override
    public UserDAO getUserDAO() throws FactoryException {
        UserDAOMySQL userDAO = new UserDAOMySQL();
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
        TariffDAOMySQL tariffDAO = new TariffDAOMySQL();
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
        ServiceDAOMySQL serviceDAO = new ServiceDAOMySQL();
        serviceDAO.setConnection(getConnection());
        return serviceDAO;
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
