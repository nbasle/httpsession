package com.yaps.petstore.server.domain.order;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.CreateException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.Address;
import com.yaps.petstore.server.domain.CreditCard;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.customer.Customer;

import java.util.Collection;
import java.util.Date;

/**
 * An order represents the items that a customer buys. This order has several
 * order items and is relevant for one customer. The order has address information
 * like the street, the city, the country... This is because a customer can order
 * a pet and wants it delivered at another adress. By default, the order address
 * is the same that the customer's one but it can be changed.
 *
 * @see Customer
 */
public final class Order extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private Date _orderDate;
    private String _firstname;
    private String _lastname;
    private final Address _address = new Address();
    private final CreditCard _creditCard = new CreditCard();
    private Customer _customer;
    private Collection _orderLines;

    // Used to get a unique id with the UniqueIdGenerator
    private static final String COUNTER_NAME = "Order";

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new OrderDAO();
    }

    public Order() {
    }

    public Order(final String id) {
        setId(id);
    }

    public Order(final String id, final Date orderDate, final String firstname, final String lastname, final String street1, final String city, final String zipcode, final String country, final Customer customer) {
        setId(id);
        setOrderDate(orderDate);
        setFirstname(firstname);
        setLastname(lastname);
        setStreet1(street1);
        setCity(city);
        setZipcode(zipcode);
        setCountry(country);
        setCustomer(customer);
    }

    public Order(final String firstname, final String lastname, final String street1, final String city, final String zipcode, final String country, final Customer customer) {
        setFirstname(firstname);
        setLastname(lastname);
        setStreet1(street1);
        setCity(city);
        setZipcode(zipcode);
        setCountry(country);
        setCustomer(customer);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public PersistentObject create() throws CreateException, CheckException {
        final String mname = "create";
        Trace.entering(getCname(), mname);

        // Gets a unique id and the current date
        setId(getUniqueId(COUNTER_NAME));
        setOrderDate(new Date());

        // Checks data integrity
        checkData();

        // Uses the DAO to access the persistent layer
        getDAO().insert(this);

        return this;
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    protected void loadObject(final Object object) {
        final Order temp = (Order) object;

        // Sets data to current object
        setId(temp.getId());
        setFirstname(temp.getFirstname());
        setLastname(temp.getLastname());
        setCity(temp.getCity());
        setCountry(temp.getCountry());
        setCreditCardExpiryDate(temp.getCreditCardExpiryDate());
        setCreditCardNumber(temp.getCreditCardNumber());
        setCreditCardType(temp.getCreditCardType());
        setCustomer(temp.getCustomer());
        setOrderDate(temp.getOrderDate());
        setState(temp.getState());
        setStreet1(temp.getStreet1());
        setStreet2(temp.getStreet2());
        setZipcode(temp.getZipcode());
    }

    protected void checkData() throws CheckException {
        checkId(getId());
        if (getFirstname() == null || "".equals(getFirstname()))
            throw new CheckException("Invalid first name");
        if (getLastname() == null || "".equals(getLastname()))
            throw new CheckException("Invalid last name");
        if (getCity() == null || "".equals(getCity()))
            throw new CheckException("Invalid city");
        if (getCountry() == null || "".equals(getCountry()))
            throw new CheckException("Invalid country");
        if (getStreet1() == null || "".equals(getStreet1()))
            throw new CheckException("Invalid street");
        if (getZipcode() == null || "".equals(getZipcode()))
            throw new CheckException("Invalid zipcode");
        if (getCustomer() == null)
            throw new CheckException("Invalid customer");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    private OrderDAO getDAO() {
        return (OrderDAO) _dao;
    }

    public Date getOrderDate() {
        return _orderDate;
    }

    private void setOrderDate(final Date orderDate) {
        _orderDate = orderDate;
    }

    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(final String firstname) {
        _firstname = firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public void setLastname(final String lastname) {
        _lastname = lastname;
    }

    public String getStreet1() {
        return _address.getStreet1();
    }

    public void setStreet1(final String street1) {
        _address.setStreet1(street1);
    }

    public String getStreet2() {
        return _address.getStreet2();
    }

    public void setStreet2(final String street2) {
        _address.setStreet2(street2);
    }

    public String getCity() {
        return _address.getCity();
    }

    public void setCity(final String city) {
        _address.setCity(city);
    }

    public String getState() {
        return _address.getState();
    }

    public void setState(final String state) {
        _address.setState(state);
    }

    public String getZipcode() {
        return _address.getZipcode();
    }

    public void setZipcode(final String zipcode) {
        _address.setZipcode(zipcode);
    }

    public String getCountry() {
        return _address.getCountry();
    }

    public void setCountry(final String country) {
        _address.setCountry(country);
    }

    public CreditCard getCreditCard() {
        return _creditCard;
    }

    public String getCreditCardNumber() {
        return _creditCard.getCreditCardNumber();
    }

    public void setCreditCardNumber(final String creditCardNumber) {
        _creditCard.setCreditCardNumber(creditCardNumber);
    }

    public String getCreditCardType() {
        return _creditCard.getCreditCardType();
    }

    public void setCreditCardType(final String creditCardType) {
        _creditCard.setCreditCardType(creditCardType);
    }

    public String getCreditCardExpiryDate() {
        return _creditCard.getCreditCardExpiryDate();
    }

    public void setCreditCardExpiryDate(final String creditCardExpiryDate) {
        _creditCard.setCreditCardExpiryDate(creditCardExpiryDate);
    }

    public Customer getCustomer() {
        return _customer;
    }

    public void setCustomer(final Customer customer) {
        _customer = customer;
    }

    public Collection getOrderLines() {
        return _orderLines;
    }

    public void setOrderLines(final Collection orderLines) {
        _orderLines = orderLines;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Order{");
        buf.append("id=").append(getId());
        buf.append(",orderDate=").append(getOrderDate());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",state=").append(getState());
        buf.append(",zipcode=").append(getZipcode());
        buf.append(",country=").append(getCountry());
        buf.append(",creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append('}');
        return buf.toString();
    }
}
