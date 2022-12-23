package com.myfp.fp.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance;

    /**
     * Private constructor to prevent instantiation from outside
     */
    private ConnectionPool() {
    }

    /**
     * Public method to get the singleton instance of the {@link ConnectionPool} class
     *
     * @return the singleton instance of the {@link ConnectionPool} class
     */
    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    /**
     * Returns an available connection from the pool using Apache Tomcat Pool
     *
     * @return a connection to the database
     * @throws SQLException if there is an error establishing the connection
     */
    public static Connection getConnection() throws SQLException {
        Context cxt;
        Connection c = null;
        try {
            cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
            c = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return c;
    }
}
