package com.yaps.petstore;

import com.yaps.petstore.server.util.persistence.DataAccessConstants;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractTestCase extends TestCase {

    protected AbstractTestCase() {
    }

    protected AbstractTestCase(String id) {
        super(id);
    }

    protected int getUniqueId() {
        return (int) (Math.random() * 100000);
    }

    protected String getUniqueStringId() {
        int id = (int) (Math.random() * 100000);
        return "" + id;
    }

    protected Connection getConnection() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(DataAccessConstants.URL_DB, DataAccessConstants.USER_DB, DataAccessConstants.PASSWD_DB);
        return connection;
    }
}
