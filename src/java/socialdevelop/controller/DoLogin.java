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
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 * @author Mario Vetrini
 */
public class DoLogin extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }
    
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        HttpSession s = request.getSession(true);
        String user_email = request.getParameter("user_email");
        String password = request.getParameter("password");
        Utente utente;
        if (user_email != null && !user_email.equals("") && password != null && !password.equals("")) {
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            if (user_email.contains("@")){
                utente = datalayer.getUtenteByEmail(user_email);
            } else {
                utente = datalayer.getUtenteByUsername(user_email);
            }
            if (utente != null) {
                password = datalayer.GeneraPasswordMD5(password);
                if (utente.getPassword().equals(password)) {
                    SecurityLayer.createSession(request, utente.getUsername(), utente.getKey());
                    response.sendRedirect("Index");
                } else { // Password inserita in modo errato
                    s.setAttribute("errore", "password_errata");
                    response.sendRedirect("Login");
                }
            } else { // Username o email non esistenti
                s.setAttribute("errore", "user_email_errata");
                response.sendRedirect("Login");
            }
        } else {
            s.setAttribute("errore", "vuoto_o_nullo");
            response.sendRedirect("Login");
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