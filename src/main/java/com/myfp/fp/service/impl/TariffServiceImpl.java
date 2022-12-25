package com.myfp.fp.service.impl;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;

import java.util.List;

/**
 * TariffServiceImpl is an implementation of the {@link TariffService} interface. It provides
 * <p>
 * methods for interacting with the database to perform operations related to tariffs.
 *
 * <p>The service object is initialized with a {@link TariffDAO} object, which is used to access the
 * database. This allows for a separation of concerns between the service layer and the data access layer.
 */
public class TariffServiceImpl implements TariffService {
    private TariffDAO tariffDAO;

    /**
     * Sets the {@link TariffDAO} object to be used by this service object.
     *
     * @param tariffDAO the {@link TariffDAO} object to be used by this service object
     */
    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }

    /**
     * Retrieves a list of all tariffs.
     *
     * @return a list of {@link Tariff}
     * @throws ServiceException if an error occurs while retrieving the tariffs
     */
    @Override
    public List<Tariff> findAll() throws ServiceException {
        try {
            return tariffDAO.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a list of tariffs with a specified limit and offset.
     *
     * @param limit  the maximum number of tariffs to return
     * @param offset the number of tariffs to skip before returning the result
     * @return a list of {@link Tariff}
     * @throws ServiceException if an error occurs while retrieving the tariffs
     */
    @Override
    public List<Tariff> findAll(int limit, int offset) throws ServiceException {
        try {
            return tariffDAO.readAll(limit, offset);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a list of tariffs with a specified limit, offset, and order.
     *
     * @param limit  the maximum number of tariffs to return
     * @param offset the number of tariffs to skip before returning the result
     * @param order  the order in which to sort the tariffs. Possible values are "asc" for ascending order and "desc" for descending order.
     * @return a list of {@link Tariff}
     * @throws ServiceException if an error occurs while retrieving the tariffs
     */
    @Override
    public List<Tariff> findAll(int limit, int offset, String order) throws ServiceException {
        try {
            return tariffDAO.readAll(limit, offset, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a list of tariffs with a specified limit, offset, order by field, and order.
     *
     * @param limit   the maximum number of tariffs to return
     * @param offset  the number of tariffs to skip before returning the result
     * @param orderBy the field by which to sort the tariffs. Possible values are "name" and "cost".
     * @param order   the order in which to sort the tariffs. Possible values are "asc" for ascending order and "desc" for descending order.
     * @return a list of {@link Tariff}
     * @throws ServiceException if an error occurs while retrieving the tariffs
     */
    @Override
    public List<Tariff> findAll(int limit, int offset, String orderBy, String order) throws ServiceException {
        try {
            return tariffDAO.readAll(limit, offset, orderBy, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Inserts a new tariff into the database.
     *
     * @param tariff the tariff to insert
     * @return the ID of the inserted tariff
     * @throws ServiceException if an error occurs while inserting the tariff
     */
    @Override
    public Long insertTariff(Tariff tariff) throws ServiceException {
        try {
            return tariffDAO.create(tariff);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes a tariff from the database by its ID.
     *
     * @param id the ID of the tariff to delete
     * @throws ServiceException if an error occurs while deleting the tariff
     */
    @Override
    public void deleteById(Long id) throws ServiceException {
        try {
            tariffDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates an existing tariff in the database.
     *
     * @param tariff the tariff to update
     * @throws ServiceException if an error occurs while updating the tariff
     */
    @Override
    public void update(Tariff tariff) throws ServiceException {
        try {
            tariffDAO.update(tariff);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves a tariff by its ID.
     *
     * @param id the ID of the tariff to retrieve
     * @return the {@link Tariff} with the specified ID, or null if no such tariff exists
     * @throws ServiceException if an error occurs while retrieving the tariff
     */
    @Override
    public Tariff findById(Long id) throws ServiceException {
        try {
            return tariffDAO.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Retrieves the total number of tariffs in the database.
     *
     * @return the total number of tariffs in the database
     * @throws ServiceException if an error occurs while retrieving the number of tariffs
     */
    @Override
    public Integer getNoOfRecords() throws ServiceException {
        try {
            return tariffDAO.getNoOfRecords();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
