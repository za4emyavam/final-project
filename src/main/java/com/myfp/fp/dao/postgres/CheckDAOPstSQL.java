package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.CheckDAO;
import com.myfp.fp.dao.DAOException;
import com.myfp.fp.entities.Check;
import com.myfp.fp.entities.Role;
import com.myfp.fp.entities.User;
import com.myfp.fp.entities.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CheckDAOPstSQL extends BaseDAOImpl implements CheckDAO {
    private static final Logger LOG4J = LogManager.getLogger(CheckDAOPstSQL.class);
    /**

     Retrieves the most recent check from the database.
     @return the most recent check or {@code null} if no checks are found
     @throws DAOException if there is an error executing the query
     */
    @Override
    public Check readLast() throws DAOException {
        Check res = null;
        String sql = "SELECT * FROM checks c " +
                "JOIN \"user\" u ON u.user_id = c.checker_id ORDER BY c.date_of_check DESC LIMIT 1";
        Connection con = getConnection();
        try(Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                res = fillCheck(rs);
            }
            return res;
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public Check read(Long id) throws DAOException {
        return null;
    }

    @Override
    public Long create(Check entity) throws DAOException {
        return null;
    }

    @Override
    public void update(Check entity) throws DAOException {

    }

    @Override
    public void delete(Long id) throws DAOException {

    }

    private Check fillCheck(ResultSet rs) throws DAOException {
        Check check = new Check();

        try {
            check.setId((long) rs.getInt("check_id"));

            User user = new User();
            user.setId(rs.getLong("user_id"));
            user.setEmail(rs.getString("email"));
            user.setPass(rs.getString("pass"));
            user.setUserBalance(rs.getInt("user_balance"));
            user.setFirstname(rs.getString("firstname"));
            user.setMiddleName(rs.getString("middle_name"));
            user.setSurname(rs.getString("surname"));
            user.setTelephoneNumber(rs.getString("telephone_number"));
            user.setRegistrationDate(rs.getDate("registration_date"));
            user.setUserRole(Role.fromString(rs.getString("user_role")));
            user.setUserStatus(UserStatus.fromString(rs.getString("user_status")));
            check.setCheckerId(user);

            check.setUsers(rs.getInt("users"));
            check.setAmount(rs.getInt("amount"));
            check.setDateOfCheck(new Date(rs.getTimestamp("date_of_check").getTime()));
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return check;
    }
}
