package com.yaps.petstore.server.domain.customer;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.Address;
import com.yaps.petstore.server.domain.CreditCard;
import com.yaps.petstore.server.domain.PersistentObject;

/**
 * This class represents a customer for the YAPS company.
 */
public final class Customer extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _firstname;
    private String _lastname;
    private String _telephone;
    private String _email;
    private String _password;
    private final Address _address = new Address();
    private final CreditCard _creditCard = new CreditCard();

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new CustomerDAO();
    }

    public Customer() {
    }

    public Customer(final String id) {
        setId(id);
    }

    public Customer(final String id, final String firstname, final String lastname) {
        setId(id);
        setFirstname(firstname);
        setLastname(lastname);
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    protected void loadObject(final Object object) {
        final Customer temp = (Customer) object;

        // Sets data to current object
        setId(temp.getId());
        setFirstname(temp.getFirstname());
        setLastname(temp.getLastname());
        setTelephone(temp.getTelephone());
        setEmail(temp.getEmail());
        setStreet1(temp.getStreet1());
        setStreet2(temp.getStreet2());
        setCity(temp.getCity());
        setState(temp.getState());
        setZipcode(temp.getZipcode());
        setCountry(temp.getCountry());
        setCreditCardNumber(temp.getCreditCardNumber());
        setCreditCardType(temp.getCreditCardType());
        setCreditCardExpiryDate(temp.getCreditCardExpiryDate());
        setPassword(temp.getPassword());
    }

    protected void checkData() throws CheckException {
        checkId(getId());
        if (getFirstname() == null || "".equals(getFirstname()))
            throw new CheckException("Invalid first name");
        if (getLastname() == null || "".equals(getLastname()))
            throw new CheckException("Invalid last name");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
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

    public String getTelephone() {
        return _telephone;
    }

    public void setTelephone(final String telephone) {
        _telephone = telephone;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(final String email) {
        _email = email;
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

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Customer{");
        buf.append("id=").append(getId());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",telephone=").append(getTelephone());
        buf.append(",email=").append(getEmail());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",state=").append(getState());
        buf.append(",zipcode=").append(getZipcode());
        buf.append(",country=").append(getCountry());
        buf.append(",creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append(", password=").append(getPassword());
        buf.append('}');
        return buf.toString();
    }

	/**
	 * @param password
	 */
	public void matchPassword(final String password) throws CheckException {
		if (!password.equals(_password))
		throw new CheckException("Invalid password!!!");
	}

	/**
	 * @param password
	 */
	public void setPassword(final String password) {
		_password = password;
		
	}

	/**
	 * @return
	 */
	public String getPassword() {		
		return _password;
	}
}
