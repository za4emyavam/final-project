package com.myfp.fp.service.impl;

import com.myfp.fp.dao.CheckDAO;
import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.Check;
import com.myfp.fp.service.CheckService;
import com.myfp.fp.service.ServiceException;

/**

 CheckServiceImpl is an implementation of the {@link CheckService} interface. It provides methods for interacting

 with the database to perform operations related to checks.

 <p>The service object is initialized with a {@link CheckDAO} object, which is used to access the database. This
 allows for a separation of concerns between the service layer and the data access layer.

 */
public class CheckServiceImpl implements CheckService {
    private CheckDAO checkDAO;

    /**

     Sets the {@link CheckDAO} object to be used by this service object.
     @param checkDAO the {@link CheckDAO} object to be used by this service object
     */
    public void setCheckDAO(CheckDAO checkDAO) {
        this.checkDAO = checkDAO;
    }

    /**

     Reads the last check from the database.
     @return the last check from the database
     @throws ServiceException if there is an error performing the operation
     */
    @Override
    public Check readLast() throws ServiceException {
        try {
            return checkDAO.readLast();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
