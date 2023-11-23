/*
 * Created on 8 janv. 2006
 *
 */
package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.FinderException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yaps.petstore.common.logging.Trace;

/**
 * @author Veronique
 *
 */
public class SignOnServlet extends AbstractServlet {
	protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String mname="service";
		Trace.entering(getCname(), mname);
		
		 
		HttpSession sessionScope = request.getSession(true);
		final CustomerDTO tempcustomerDTO;
		CustomerDTO customerDTO = (CustomerDTO)sessionScope.getAttribute("customerDTO");
		String customerId = request.getParameter("customerId");
		String password = request.getParameter("password");
		try {
        //	Authenticate the customer			 
			tempcustomerDTO = CustomerDelegate.authenticate(customerId, password);
		
       
		if (customerDTO==null)	{
			if(tempcustomerDTO !=null) {
//				puts the customerDTO into the session
				sessionScope.setAttribute("customerDTO", tempcustomerDTO);
			}
		}
							
		
		// Goes to the index page passing the request
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		
		} catch(FinderException e) {
			getServletContext().getRequestDispatcher("/error.jsp?exception="+e.getMessage()).forward(request, response);
		} catch(CheckException e) {
			getServletContext().getRequestDispatcher("/error.jsp?exception="+e.getMessage()).forward(request, response);
		} catch(Exception e){
			Trace.throwing(getCname(), mname, e);
			getServletContext().getRequestDispatcher("/error.jsp?exception=Authenticatio failed").forward(request, response);
		}
		
	}

}
