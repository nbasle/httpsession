package com.yaps.petstore.server.service.creditcard;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.CreditCard;
import org.dom4j.Document;

/**
 * This class verifies a credit card with the barkbank's credit card service.
 *
 * @see com.barkbank.verifier.VerifyCreditCardServlet
 */
public final class CreditCardService {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // XPath
    private static final String XPATH_VERIFIER_STATUS = "//CreditCard/@Status";

    // ======================================
    // =           Business methods         =
    // ======================================
    public void verifyCreditCard(CreditCard creditCard) throws CheckException {
        String mname = "verifyCreditCard";
        Trace.entering(_cname, mname, creditCard);

        // The credit card hasn't been used. The customer has paid by check
        if ((creditCard.getCreditCardType() == null || "".equals(creditCard.getCreditCardType())) &&
                (creditCard.getCreditCardNumber() == null || "".equals(creditCard.getCreditCardNumber())) &&
                (creditCard.getCreditCardExpiryDate() == null || "".equals(creditCard.getCreditCardExpiryDate())))
            return;

        // Sends the XML stream to the servlet that will verify the credit card
        Document creditCardVerifiedXML = HTTPSender.send(creditCard.toXML());

        // Gets the response from the servlet and analyses the response
        analyseResponse(creditCardVerifiedXML);

        Trace.exiting(_cname, mname);
    }

    // ======================================
    // =           Private methods          =
    // ======================================
    private static void analyseResponse(Document creditCardVerifiedXML) throws CheckException {

        // Gets the status from the XML document
        String status = creditCardVerifiedXML.selectSingleNode(XPATH_VERIFIER_STATUS).getText();

        // If the credit card is not 'Valid' an exception is thrown
        if (!"Valid".equals(status))
            throw new CheckException("Invalid Credit Card: " + status);
    }
}
