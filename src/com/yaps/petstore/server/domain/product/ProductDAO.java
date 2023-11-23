package com.yaps.petstore.server.domain.product;

import com.yaps.petstore.common.exception.DataAccessException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class does all the database access for the class Product.
 *
 * @see Product
 */
final class ProductDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_PRODUCT";
    private static final String COLUMNS = "ID, NAME, DESCRIPTION, CATEGORY_FK";

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method return all the products from the database for a given category id.
     *
     * @param categoryId
     * @return collection of Product
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws DataAccessException     is thrown if there's a persistent problem
     */
    public Collection selectAll(final String categoryId) throws ObjectNotFoundException {
        final String mname = "selectAll";
        Trace.entering(getCname(), mname, categoryId);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final Collection products = new ArrayList();

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE CATEGORY_FK = '" + categoryId + "'";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Set data to the collection
                products.add(transformResultset2PersistentObject(resultSet));
            }

            if (products.isEmpty())
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
        return products;
    }

    protected String getInsertSqlStatement(final PersistentObject object) {
        final Product product = (Product) object;
        final String sql;
        sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + product.getId() + "', '" + product.getName() + "', '" + product.getDescription() + "', '" + product.getCategory().getId() + "' )";
        return sql;
    }

    protected String getDeleteSqlStatement(final String id) {
        final String sql;
        sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
        return sql;
    }

    protected String getUpdateSqlStatement(final PersistentObject object) {
        final Product product = (Product) object;
        final String sql;
        sql = "UPDATE " + TABLE + " SET NAME = '" + product.getName() + "', DESCRIPTION = '" + product.getDescription() + "', CATEGORY_FK = '" + product.getCategory().getId() + "' WHERE ID = '" + product.getId() + "' ";
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
        final Product product;
        product = new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), new Category(resultSet.getString(4)));
        return product;
    }
}
