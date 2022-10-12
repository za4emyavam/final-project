package com.myfp.fp.dao.mysql;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.ServiceDAO;
import com.myfp.fp.entities.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOMySQL extends BaseDAOImpl implements ServiceDAO {
    @Override
    public Service read(Long id) throws DAOException {
        /*Service res = null;
        return res;*/
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
                service.setId(rs.getLong("id"));
                service.setType(rs.getString("type"));
                services.add(service);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return services;
    }

    @Override
    public Service readByType(String str) throws DAOException {
        Service res = null;
        String sql = "SELECT s.id FROM service s WHERE s.type=?";
        Connection con = getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, str);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Service service = new Service();
                    service.setType(str);
                    service.setId(rs.getLong("id"));
                    return service;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }
}
