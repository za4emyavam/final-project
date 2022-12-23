package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.ServiceDAO;
import com.myfp.fp.entities.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOPstSQL extends BaseDAOImpl implements ServiceDAO {
    private static final Logger LOG4J = LogManager.getLogger(ServiceDAOPstSQL.class);
    @Override
    public Service read(Long id) throws DAOException {
        return null;
    }

    @Override
    public Long create(Service entity) throws DAOException {
        return null;
    }

    @Override
    public void update(Service entity) throws DAOException {

    }

    @Override
    public void delete(Long id) throws DAOException {

    }

    /**

     Retrieves a list of all services from the database.
     @return a list of services
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<Service> readAll() throws DAOException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM service";
        Connection con = getConnection();
        try(ResultSet rs = con.createStatement().executeQuery(sql)) {
            while(rs.next()) {
                Service service = new Service();
                service.setId(rs.getLong("service_id"));
                service.setServiceType(rs.getString("service_type"));
                services.add(service);
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return services;
    }

    /**

     Retrieves a service from the database by service type.
     @param str the type of the service to retrieve
     @return the service with the given type, or {@code null} if no service is found
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public Service readByType(String str) throws DAOException {
        String sql = "SELECT s.service_id FROM service s WHERE s.service_type=?";
        Connection con = getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, str);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Service service = new Service();
                    service.setServiceType(str);
                    service.setId(rs.getLong("service_id"));
                    return service;
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return null;
    }
}
