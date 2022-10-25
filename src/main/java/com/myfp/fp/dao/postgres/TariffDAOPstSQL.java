package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.entities.TariffStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TariffDAOPstSQL extends BaseDAOImpl implements TariffDAO {
    @Override
    public Tariff read(Long id) throws DAOException {
        String sql = "SELECT * FROM tariff t, service s WHERE t.tariff_id=? AND t.service=s.service_id";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Tariff res = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    res = fillTariff(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return res;
    }

    @Override
    public Long create(Tariff entity) throws DAOException {
        String sql = "INSERT INTO tariff(name, description, service, cost, frequency_of_payment, status) VALUES(?, ?, ?, ?, ?, DEFAULT)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        long resultId = -1;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, entity.getName());
            preparedStatement.setString(k++, entity.getDescription());
            preparedStatement.setInt(k++, Math.toIntExact(entity.getService().getId()));
            preparedStatement.setInt(k++, entity.getCost());
            preparedStatement.setInt(k, entity.getFrequencyOfPayment());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                try(ResultSet set = preparedStatement.getGeneratedKeys()) {
                    if(set.next()) {
                        entity.setId(resultId = set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return resultId;
    }

    @Override
    public void update(Tariff entity) throws DAOException {
        String sql = "UPDATE tariff t " +
                "SET name=?, description=?, service=?, cost=?, frequency_of_payment=?, status=?::tariff_status_type " +
                "WHERE t.tariff_id=" + entity.getId();
        Connection con = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(sql);
            int k = 1;
            preparedStatement.setString(k++, entity.getName());
            preparedStatement.setString(k++, entity.getDescription());
            preparedStatement.setInt(k++, Math.toIntExact(entity.getService().getId()));
            preparedStatement.setInt(k++, entity.getCost());
            preparedStatement.setInt(k++, entity.getFrequencyOfPayment());
            preparedStatement.setObject(k, entity.getTariffStatus().getName());
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        String sql = "DELETE FROM tariff t WHERE t.tariff_id=?";
        Connection con = getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, Math.toIntExact(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public List<Tariff> readAll() throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        String sql = "SELECT /*t.id, t.name, t.description, t.cost, t.frequency_of_payment, s.id, s.type*/* " +
                "FROM tariff t, service s WHERE t.service=s.service_id";
        Connection con = getConnection();
        try(Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while(resultSet.next()) {
                tariffs.add(fillTariff(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return tariffs;
    }

    private Tariff fillTariff(ResultSet rs) throws SQLException {
        Tariff tariff = new Tariff();
        try {
            tariff.setId(rs.getLong("tariff_id"));
            tariff.setName(rs.getString("name"));
            tariff.setDescription(rs.getString("description"));
            tariff.setCost(rs.getInt("cost"));
            tariff.setFrequencyOfPayment(rs.getInt("frequency_of_payment"));
            tariff.setTariffStatus(TariffStatus.fromString(rs.getString("status")));

            Service service = new Service();
            service.setId(rs.getLong("service_id"));
            service.setServiceType(rs.getString("service_type"));
            tariff.setService(service);
        } catch (SQLException e) {
            throw e;
        }
        return tariff;
    }
}
