package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.ServiceDAO;
import com.myfp.fp.entities.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOPstSQL extends BaseDAOImpl implements ServiceDAO {
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
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return services;
    }

    @Override
    public Service readByType(String str) throws DAOException {
        Service res = null;
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
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return null;
    }
}
