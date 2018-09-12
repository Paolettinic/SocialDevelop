package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Amministratore;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 * @author Nicolò Paoletti
 */
public class DoLoginAdmin extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }
    
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        HttpSession s = request.getSession(true);
        String user_email = request.getParameter("email");
        String password = request.getParameter("password");
        Amministratore admin;
        if (user_email != null && !user_email.equals("") && password != null && !password.equals("")) {
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            if (user_email.contains("@")){
                admin = datalayer.getAmministratoreByEmail(user_email);
            } else {
                admin = datalayer.getAmministratoreByUsername(user_email);
            }
            if (admin != null) {
                password = datalayer.GeneraPasswordMD5(password);
                if (admin.getPassword().equals(password)) {
                    SecurityLayer.createSession(request, admin.getUsername(), admin.getKey());
                    response.sendRedirect("type");
                } else { // Password inserita in modo errato
                    s.setAttribute("errore", "password_errata");
                    response.sendRedirect("admin");
                }
            } else { // Username o email non esistenti
                s.setAttribute("errore", "admin_email_errata");
                response.sendRedirect("admin");
            }
        } else {
            s.setAttribute("errore", "vuoto_o_nullo");
            response.sendRedirect("admin");
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