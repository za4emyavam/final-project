package com.myfp.fp.controller;

import com.myfp.fp.util.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionListener implements ServletContextListener {
    /**
     * Initializes the ServletContext by getting a connection from the connection pool.
     * If the connection is null or an SQLException is thrown while trying to get the connection,
     * the context is destroyed and a RuntimeException is thrown with the message
     * "Error getting Connection to database".
     *
     * @param sce the ServletContextEvent
     * @throws RuntimeException if there is an error getting the Connection to the database
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Connection con = ConnectionPool.getConnection();
            if (con == null) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            contextDestroyed(sce);
            throw new RuntimeException("Error getting Connection to database");
        }
    }
}
