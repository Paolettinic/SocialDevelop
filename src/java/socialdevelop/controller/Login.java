package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Mario Vetrini
 */

public class Login extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        request.setAttribute("page_title", "Login");
        HttpSession s = request.getSession(true);
        if (s.getAttribute("userid") != null && ((int) s.getAttribute("userid")) > 0) {
            response.sendRedirect("Index");
        } else {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("login.html", request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_login(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException | SQLException | NamingException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
