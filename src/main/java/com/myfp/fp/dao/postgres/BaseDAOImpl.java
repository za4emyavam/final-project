package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**

 Abstract base class for data access objects that defines common methods for handling connections, statements, and

 result sets.
 */
abstract public class BaseDAOImpl {
    private static final Logger LOG4J = LogManager.getLogger(BaseDAOImpl.class);

    /**

     Retrieves a connection from the connection pool using Apache Tomcat Pool.
     @return a connection to the database
     @throws DAOException if there is an error establishing the connection
     */
    public Connection getConnection() throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
        }
        return con;
    }

    /**

     Closes a connection.
     @param con the connection to be closed
     @throws DAOException if an error occurs while closing the connection
     */
    void closeConnection(Connection con) throws DAOException {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                LOG4J.error(e.getMessage(), e);
                throw new DAOException(e);
            }
        }
    }

    /**

     Rolls back a transaction.
     @param con the connection on which to roll back the transaction
     @throws DAOException if an error occurs while rolling back the transaction
     */
    void rollback(Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        }
    }

    /**

     Closes the given AutoCloseable object, such as a Statement or ResultSet. If an exception occurs, it is logged and a DAOException is thrown.
     @param con the AutoCloseable object to close
     @throws DAOException if an exception occurs while closing the object
     */
    void closeStat(AutoCloseable con) throws DAOException {
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        }
    }
}
