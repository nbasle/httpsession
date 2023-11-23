package com.yaps.petstore.server.domain.category;

import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class does all the database access for the class Category.
 *
 * @see Category
 */
final class CategoryDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_CATEGORY";
    private static final String COLUMNS = "ID, NAME, DESCRIPTION";

    // ======================================
    // =           Business methods         =
    // ======================================
    protected String getInsertSqlStatement(final PersistentObject object) {
        final Category category = (Category) object;
        final String sql;
        sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + category.getId() + "', '" + category.getName() + "', '" + category.getDescription() + "' )";
        return sql;
    }

    protected String getDeleteSqlStatement(final String id) {
        final String sql;
        sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
        return sql;
    }

    protected String getUpdateSqlStatement(final PersistentObject object) {
        final Category category = (Category) object;
        final String sql;
        sql = "UPDATE " + TABLE + " SET NAME = '" + category.getName() + "', DESCRIPTION = '" + category.getDescription() + "' WHERE ID = '" + category.getId() + "' ";
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
        final Category category;
        category = new Category(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        return category;
    }
}
