package com.myfp.fp.dao.postgres;

import com.myfp.fp.dao.DAOException;
import com.myfp.fp.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

abstract public class BaseDAOImpl {
    private static final Logger LOG4J = LogManager.getLogger(BaseDAOImpl.class);

    public Connection getConnection() throws DAOException {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
        }
        return con;
    }

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

    void rollback(Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException e) {
            LOG4J.error(e.getMessage(), e);
            throw new DAOException(e);
        }
    }

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
