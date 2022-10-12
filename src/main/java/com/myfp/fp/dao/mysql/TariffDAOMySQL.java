package com.myfp.fp.dao.mysql;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TariffDAOMySQL extends BaseDAOImpl implements TariffDAO {
    @Override
    public Tariff read(Long id) throws DAOException {
        String sql = "SELECT * FROM tariff t, service s WHERE t.id=? AND t.type=s.id";
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
        }
        return res;
    }

    @Override
    public Long create(Tariff entity) throws DAOException {
        String sql = "INSERT tariff(name, description, cost, frequency_of_payment, type) VALUES(?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        long resultId = -1;
        try {
            con = getConnection();
            System.out.println("after get connection");
            System.out.println("change auto commit");
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            System.out.println("check 0");
            preparedStatement.setString(k++, entity.getName());
            preparedStatement.setString(k++, entity.getDescription());
            preparedStatement.setInt(k++, entity.getCost());
            preparedStatement.setInt(k++, entity.getFrequencyOfPayment());
            preparedStatement.setInt(k, Math.toIntExact(entity.getType().getId()));
            System.out.println("check 1");
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("check 2");
                try(ResultSet set = preparedStatement.getGeneratedKeys()) {
                    if(set.next()) {
                        System.out.println("check 3");
                        entity.setId(resultId = set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStat(preparedStatement);
        }
        return resultId ;
    }

    @Override
    public void update(Tariff entity) throws DAOException {
        String sql = "UPDATE tariff t SET t.name=?, t.description=?, t.cost=?, t.frequency_of_payment=? WHERE t.id=" + entity.getId();
        Connection con = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);
            int k = 1;
            preparedStatement.setString(k++, entity.getName());
            preparedStatement.setString(k++, entity.getDescription());
            preparedStatement.setInt(k++, entity.getCost());
            preparedStatement.setInt(k, entity.getFrequencyOfPayment());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        String sql = "DELETE FROM tariff t WHERE t.id=?";
        Connection con = getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, Math.toIntExact(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Tariff> readAll() throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        String sql = "SELECT t.id, t.name, t.description, t.cost, t.frequency_of_payment, s.id, s.type " +
                "FROM tariff t, service s WHERE t.type=s.id";
        Connection con = getConnection();
        try(/*Connection con = getConnection();*/
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while(resultSet.next()) {
                tariffs.add(fillTariff(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tariffs;
    }

    private Tariff fillTariff(ResultSet resultSet) throws SQLException{
        Tariff tariff = new Tariff();
        tariff.setId(resultSet.getLong("id"));
        tariff.setName(resultSet.getString("name"));
        tariff.setDescription(resultSet.getString("description"));
        tariff.setCost(resultSet.getInt("cost"));
        tariff.setFrequencyOfPayment(resultSet.getInt("frequency_of_payment"));
        Service service = new Service();
        service.setId(resultSet.getLong("s.id"));
        service.setType(resultSet.getString("s.type"));
        tariff.setType(service);
        return tariff;
    }
}
