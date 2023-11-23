/*
 * Created on 8 janv. 2006
 * 
 */
package com.yaps.petstore.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yaps.petstore.common.logging.Trace;

/**
 * @author Veronique
 *
 */
public class UpdateCustomerServlet extends AbstractServlet {
	
	protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String mname ="service";
		Trace.entering(getCname(), mname);
		
		// Goes to the index page passing the request
		getServletContext().getRequestDispatcher("/createcustomer.jsp").forward(request, response);
		
	}

}
