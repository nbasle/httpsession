package com.yaps.petstore.server.service;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.CreditCard;
import com.yaps.petstore.server.service.creditcard.CreditCardService;
import junit.framework.TestSuite;

/**
 * This class tests the CreditCardService class
 */
public class CreditCardServiceTest extends AbstractTestCase {

    public CreditCardServiceTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CreditCardServiceTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This method verifies a valid credit card.
     */
    public void testServiceVerifyValidCreditCard() throws Exception {

        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber("4564 1231 4564 2222");
        creditCard.setCreditCardExpiryDate("10/08");
        creditCard.setCreditCardType("Visa");

        try {
            getCreditCardService().verifyCreditCard(creditCard);
        } catch (CheckException e) {
            fail("Credit card is valid. It should not throw an exception");
        }
    }

    /**
     * This method verifies a credit card with a old date.
     */
    public void testServiceVerifyOldCreditCard() throws Exception {

        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber("4564 1231 4564 2222");
        creditCard.setCreditCardExpiryDate("10/01");
        creditCard.setCreditCardType("Visa");

        try {
            getCreditCardService().verifyCreditCard(creditCard);
            fail("Credit card is too old. Exception should be thrown");
        } catch (CheckException e) {
        }
    }

    /**
     * This method verifies a credit card with an invalid number for a visa.
     */
    public void testServiceVerifyInvalidNumberCreditCard() throws Exception {

        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber("4564 1231 4564 1111");
        creditCard.setCreditCardExpiryDate("10/08");
        creditCard.setCreditCardType("Visa");

        try {
            getCreditCardService().verifyCreditCard(creditCard);
            fail("Credit card number is wrong. Exception should be thrown");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================
    private CreditCardService getCreditCardService() {
        return new CreditCardService();
    }
}
