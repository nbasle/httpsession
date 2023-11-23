package com.yaps.petstore.server.domain.orderline;

import com.yaps.petstore.common.exception.DataAccessException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

final class OrderLineDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_ORDER_LINE";
    private static final String COLUMNS = "ID, QUANTITY, UNITCOST, ORDER_FK, ITEM_FK";

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method return all the order line from the database for a given order id.
     *
     * @param orderId
     * @return collection of OrderLine
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws DataAccessException     is thrown if there's a persistent problem
     */
    public Collection selectAll(final String orderId) throws ObjectNotFoundException {
        final String mname = "selectAll";
        Trace.entering(getCname(), mname, orderId);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final Collection orderLines = new ArrayList();

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ORDER_FK = '" + orderId + "'";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Set data to the collection
                orderLines.add(transformResultset2PersistentObject(resultSet));
            }

            if (orderLines.isEmpty())
                throw new ObjectNotFoundException();

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot get data from the database", e);
        } finally {
            // Close
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
        return orderLines;
    }

    protected String getInsertSqlStatement(final PersistentObject object) {
        final OrderLine orderLine = (OrderLine) object;
        final String sql;
        sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + orderLine.getId() + "', '" + orderLine.getQuantity() + "', '" + orderLine.getUnitCost() + "', '" + orderLine.getOrder().getId() + "', '" + orderLine.getItem().getId() + "' )";
        return sql;
    }

    protected String getDeleteSqlStatement(final String id) {
        final String sql;
        sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
        return sql;
    }

    protected String getUpdateSqlStatement(final PersistentObject object) {
        final OrderLine orderLine = (OrderLine) object;
        final String sql;
        sql = "UPDATE " + TABLE + " SET QUANTITY = '" + orderLine.getQuantity() + "', UNITCOST = '" + orderLine.getUnitCost() + "', ITEM_FK = '" + orderLine.getItem().getId() + "' WHERE ID = '" + orderLine.getId() + "' ";
        return sql;
    }

    protected String getSelectSqlStatement(final String id) {
        final String sql;
        sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "' ";
        return sql;
    }

    protected String getSelectAllSqlStatement() {
        final String sql;
        sql = "SELECT " + COLUMNS + " FROM " + TABLE;
        return sql;
    }

    protected PersistentObject transformResultset2PersistentObject(final ResultSet resultSet) throws SQLException {
        final OrderLine orderLine;
        orderLine = new OrderLine(resultSet.getString(1), resultSet.getInt(2), resultSet.getDouble(3), new Order(resultSet.getString(4)), new Item(resultSet.getString(5)));
        return orderLine;
    }
}
