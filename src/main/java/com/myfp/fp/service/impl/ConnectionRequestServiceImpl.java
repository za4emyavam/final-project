package com.myfp.fp.service.impl;

import com.myfp.fp.dao.ConnectionRequestDAO;
import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.ConnectionRequest;
import com.myfp.fp.service.ConnectionRequestService;
import com.myfp.fp.service.ServiceException;

import java.util.List;

/**

 ConnectionRequestServiceImpl is an implementation of the {@link ConnectionRequestService} interface. It provides

 methods for interacting with the database to perform operations related to connection requests.

 <p>The service object is initialized with a {@link ConnectionRequestDAO} object, which is used to access the
 database. This allows for a separation of concerns between the service layer and the data access layer.

 */
public class ConnectionRequestServiceImpl implements ConnectionRequestService {
    private ConnectionRequestDAO connectionRequestDAO;

    /**

     Sets the {@link ConnectionRequestDAO} object to be used by this service object.
     @param connectionRequestDAO the {@link ConnectionRequestDAO} object to be used by this service object
     */
    public void setConnectionRequestDAO(ConnectionRequestDAO connectionRequestDAO) {
        this.connectionRequestDAO = connectionRequestDAO;
    }

    /**

     Inserts a new connection request into the database.
     @param entity the {@link ConnectionRequest} to be inserted
     @return the ID of the newly inserted connection request
     @throws ServiceException if there is an error performing the operation
     */
    @Override
    public Long insertRequest(ConnectionRequest entity) throws ServiceException {
        try {
            return connectionRequestDAO.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**

     Reads all connection requests from the database.
     @return a list of all {@link ConnectionRequest} in the database
     @throws ServiceException if there is an error performing the operation
     */
    @Override
    public List<ConnectionRequest> readAll() throws ServiceException {
        try {
            return connectionRequestDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**

     Reads a limited number of connection requests from the database, starting from a specified offset.
     @param limit the maximum number of connection requests to be returned
     @param offset the number of connection requests to skip before returning the results
     @return a list of {@link ConnectionRequest} in the database
     @throws ServiceException if there is an error performing the operation
     */
    @Override
    public List<ConnectionRequest> readAll(int limit, int offset) throws ServiceException {
        try {
            return connectionRequestDAO.readAll(limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**

     Reads a specific connection request from the database.
     @param id the ID of the connection request to be read
     @return the {@link ConnectionRequest} with the specified ID
     @throws ServiceException if there is an error performing the operation
     */
    @Override
    public ConnectionRequest read(Long id) throws ServiceException {
        try {
            return connectionRequestDAO.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**

     Updates a connection request in the database.
     @param entity the updated connection request to be saved in the database
     @throws ServiceException if there is an error performing the operation
     */
    @Override
    public void update(ConnectionRequest entity) throws ServiceException {
        try {
            connectionRequestDAO.update(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**

     Returns the total number of connection requests in the database.
     @return the total number of {@link ConnectionRequest}
     @throws ServiceException if there is an error performing the operation
     */
    @Override
    public Integer getNoOfRecords() throws ServiceException {
        try {
            return connectionRequestDAO.getNoOfRecords();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
