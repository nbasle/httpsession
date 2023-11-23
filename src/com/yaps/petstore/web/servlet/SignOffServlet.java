package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOffServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        // Invalidates the HTTPSession
        request.getSession().invalidate();
        Trace.finest(getCname(), mname, "http session invalidate");

        // Goes to the index page passing the request
        getServletContext().getRequestDispatcher("/signoff.jsp").forward(request, response);
    }
}
