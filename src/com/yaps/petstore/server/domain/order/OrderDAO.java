package com.yaps.petstore.server.domain.order;

import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class does all the database access for the class Order.
 *
 * @see Order
 */
final class OrderDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_ORDER";
    private static final String COLUMNS = "ID, ORDERDATE, FIRSTNAME, LASTNAME, STREET1, STREET2, CITY, STATE, ZIPCODE, COUNTRY, CREDITCARDNUMBER, CREDITCARDTYPE, CREDITCARDEXPIRYDATE, CUSTOMER_FK";

    // ======================================
    // =           Business methods         =
    // ======================================
    protected String getInsertSqlStatement(final PersistentObject object) {
        final Order order = (Order) object;
        final String sql;
        sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + order.getId() + "', '" + new Timestamp(order.getOrderDate().getTime()) + "', '" + order.getFirstname() + "', '" + order.getLastname() + "', '" + order.getStreet1() + "', '" + order.getStreet2() + "', '" + order.getCity() + "', '" + order.getState() + "', '" + order.getZipcode() + "', '" + order.getCountry() + "', '" + order.getCreditCardNumber() + "', '" + order.getCreditCardType() + "', '" + order.getCreditCardExpiryDate() + "', '" + order.getCustomer().getId() + "' )";
        return sql;
    }

    protected String getDeleteSqlStatement(final String id) {
        final String sql;
        sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
        return sql;
    }

    protected String getUpdateSqlStatement(final PersistentObject object) {
        final Order order = (Order) object;
        final String sql;
        sql = "UPDATE " + TABLE + " SET FIRSTNAME = '" + order.getFirstname() + "', LASTNAME = '" + order.getLastname() + "', STREET1 = '" + order.getStreet1() + "', STREET2 = '" + order.getStreet2() + "', CITY = '" + order.getCity() + "', STATE = '" + order.getState() + "', ZIPCODE = '" + order.getZipcode() + "', COUNTRY = '" + order.getCountry() + "', CREDITCARDNUMBER = '" + order.getCreditCardNumber() + "', CREDITCARDTYPE = '" + order.getCreditCardType() + "', CREDITCARDEXPIRYDATE = '" + order.getCreditCardExpiryDate() + "', CUSTOMER_FK = '" + order.getCustomer().getId() + "' WHERE ID = '" + order.getId() + "' ";
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
        final Order order;
        order = new Order(resultSet.getString(1), resultSet.getTimestamp(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(7), resultSet.getString(9), resultSet.getString(10), new Customer(resultSet.getString(14)));
        order.setStreet2(resultSet.getString(6));
        order.setState(resultSet.getString(8));
        order.setCreditCardNumber(resultSet.getString(11));
        order.setCreditCardType(resultSet.getString(12));
        order.setCreditCardExpiryDate(resultSet.getString(13));
        return order;
    }
}
