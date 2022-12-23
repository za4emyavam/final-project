package com.myfp.fp.service.impl;

import com.myfp.fp.dao.ConnectionRequestDAO;
import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.ServiceDAO;
import com.myfp.fp.entities.Service;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.ServiceService;

import java.util.List;

/**

 ServiceServiceImpl is an implementation of the {@link ServiceService} interface. It provides

 methods for interacting with the database to perform operations related to services.

 <p>The service object is initialized with a {@link ServiceDAO} object, which is used to access the
 database. This allows for a separation of concerns between the service layer and the data access layer.

 */
public class ServiceServiceImpl implements ServiceService {
    ServiceDAO serviceDAO;

    /**

     Sets the {@link ServiceDAO} object to be used by this service object.
     @param serviceDAO the {@link ServiceDAO} object to be used by this service object
     */
    public void setServiceDAO(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    /**
     * Finds all services.
     *
     * @return a list of {@link Service}
     * @throws ServiceException if there is an error while finding the services
     */
    @Override
    public List<Service> findAll() throws ServiceException {
        try {
            return serviceDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Finds a service by its type.
     *
     * @param type the type of the service to find
     * @return the {@link Service} with the specified type
     * @throws ServiceException if there is an error while finding the service
     */
    @Override
    public Service findByType(String type) throws ServiceException {
        try {
            return serviceDAO.readByType(type);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
