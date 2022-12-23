package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.dao.TariffDAO;
import com.myfp.fp.entities.Service;
import com.myfp.fp.entities.Tariff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TariffDAOPstSQL extends BaseDAOImpl implements TariffDAO {
    private static final Logger LOG4J = LogManager.getLogger(TariffDAOPstSQL.class);

    /**

     Reads the information for a specific tariff from the database.
     @param id The ID of user to be read.
     @return The {@link Tariff} object from the database.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public Tariff read(Long id) throws DAOException {
        String sql = "SELECT * FROM tariff t, service s WHERE t.tariff_id=? AND t.service=s.service_id";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Tariff res = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    res = fillTariff(resultSet);
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return res;
    }

    /**

     Creates a new tariff in the database.
     @param entity The {@link Tariff} object to be created.
     @return The ID of the newly created tariff.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public Long create(Tariff entity) throws DAOException {
        String sql = "INSERT INTO tariff(name, description, service, cost, frequency_of_payment) VALUES(?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        long resultId = -1;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setArray(k++, con.createArrayOf("varchar", entity.getName()));
            preparedStatement.setArray(k++, con.createArrayOf("text", entity.getDescription()));
            preparedStatement.setInt(k++, Math.toIntExact(entity.getService().getId()));
            preparedStatement.setInt(k++, entity.getCost());
            preparedStatement.setInt(k, entity.getFrequencyOfPayment());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                try (ResultSet set = preparedStatement.getGeneratedKeys()) {
                    if (set.next()) {
                        entity.setId(resultId = set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
        return resultId;
    }

    /**

     Updates the information for a specific tariff in the database.
     @param entity The {@link Tariff} object containing the updated information.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public void update(Tariff entity) throws DAOException {
        String sql = "UPDATE tariff t " +
                "SET name=?, description=?, service=?, cost=?, frequency_of_payment=? " +
                "WHERE t.tariff_id=" + entity.getId();
        Connection con = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);
            int k = 1;
            preparedStatement.setArray(k++, con.createArrayOf("varchar", entity.getName()));
            preparedStatement.setArray(k++, con.createArrayOf("text", entity.getDescription()));
            preparedStatement.setInt(k++, Math.toIntExact(entity.getService().getId()));
            preparedStatement.setInt(k++, entity.getCost());
            preparedStatement.setInt(k, entity.getFrequencyOfPayment());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
            closeStat(preparedStatement);
        }
    }

    /**

     Deletes the tariff with the specified id from the database.
     @param id the id of the tariff to delete
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public void delete(Long id) throws DAOException {
        String sql = "DELETE FROM tariff t WHERE t.tariff_id=?";
        Connection con = getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, Math.toIntExact(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
    }

    /**

     Retrieves all tariffs from the database.
     @return a list of {@link Tariff} objects representing all tariffs in the database
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<Tariff> readAll() throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        String sql = "SELECT * FROM tariff t, service s WHERE t.service=s.service_id";
        Connection con = getConnection();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                tariffs.add(fillTariff(resultSet));
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return tariffs;
    }

    /**
     * Reads a list of tariffs from the database, starting from the specified offset and limited by the specified limit.
     *
     * @param limit  the maximum number of tariffs to read
     * @param offset the offset from which to start reading tariffs
     * @return a list of {@link Tariff}
     * @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<Tariff> readAll(int limit, int offset) throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        String sql = "SELECT * FROM tariff t, service s WHERE t.service=s.service_id LIMIT ? OFFSET ?";
        Connection con = getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    tariffs.add(fillTariff(rs));
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return tariffs;
    }

    /**
     * Retrieves a list of tariffs from the database, sorted and limited according to the provided parameters.
     *
     * @param limit  the maximum number of tariffs to be returned
     * @param offset the number of tariffs to skip before returning results
     * @param order  the column to sort the results by, can be "tariff_id", "tariff_name", "monthly_price", or "service"
     * @return a list of {@link Tariff} sorted and limited according to the provided parameters
     * @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<Tariff> readAll(int limit, int offset, String order) throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        String sql = "SELECT * FROM tariff t, service s WHERE t.service=s.service_id ORDER BY " + order + " LIMIT ? OFFSET ?";
        Connection con = getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    tariffs.add(fillTariff(rs));
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return tariffs;
    }

    /**

     Retrieves a list of tariffs from the database, with an optional limit and offset to paginate the results,
     and an optional orderBy and order to specify the sorting of the results.
     @param limit the maximum number of tariffs to be retrieved.
     @param offset the number of tariffs to be skipped before starting to retrieve tariffs.
     @param orderBy the column to sort the results by.
     @param order the order in which to sort the results (either "ASC" for ascending or "DESC" for descending).
     @return a list of {@link Tariff} retrieved from the database.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public List<Tariff> readAll(int limit, int offset, String orderBy, String order) throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        String sql = "SELECT * FROM tariff t, service s WHERE t.service=s.service_id ORDER BY " + orderBy + " " + order + " LIMIT ? OFFSET ?";
        Connection con = getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    tariffs.add(fillTariff(rs));
                }
            }
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return tariffs;
    }

    /**

     Returns the total number of tariffs.
     @return the total number of tariffs as an integer.
     @throws DAOException If there is an error executing the SQL statement.
     */
    @Override
    public Integer getNoOfRecords() throws DAOException {
        String sql = "SELECT count(*) AS count FROM tariff";
        Connection con = getConnection();
        int res = -1;
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next())
                res = rs.getInt(1);
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
        return res;
    }

    /**

     Fills a {@link Tariff} object with data from the given ResultSet.
     @param rs the ResultSet from which to get the data
     @return a {@link Tariff} object filled with data from the ResultSet
     @throws SQLException if an error occurs while processing the ResultSet
     */
    private Tariff fillTariff(ResultSet rs) throws SQLException {
        Tariff tariff = new Tariff();
        try {
            tariff.setId(rs.getLong("tariff_id"));
            tariff.setName((String[]) rs.getArray("name").getArray());
            tariff.setDescription((String[]) rs.getArray("description").getArray());
            tariff.setCost(rs.getInt("cost"));
            tariff.setFrequencyOfPayment(rs.getInt("frequency_of_payment"));

            Service service = new Service();
            service.setId(rs.getLong("service_id"));
            service.setServiceType(rs.getString("service_type"));
            tariff.setService(service);
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw e;
        }
        return tariff;
    }
}
