package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 * @author Mario Vetrini
 */

public class EditUserProfileRegistry extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_edit_user_profile_registry(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        if (s.getAttribute("userid") == null) {
            request.setAttribute("utente_key", 0);
        } else {
            request.setAttribute("utente_key", (int) s.getAttribute("userid"));
        }
        
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente((int) s.getAttribute("userid"));
        if (utente != null) {
            // RECUPERO L'UTENTE DA MODIFICARE E LO SETTO NEL TEMPLATE
            request.setAttribute("profilo_key", (int) s.getAttribute("userid"));
            request.setAttribute("utente", utente);
            request.setAttribute("data_nascita", utente.getDataNascita());
            
            request.setAttribute("page_title", "Modifica i tuoi dati");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("edit_user_profile_registry.html", request, response);
        } else {
            response.sendRedirect("Index");
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession s = request.getSession(true);
        try {
            if (s.getAttribute("userid") != null) {
                action_edit_user_profile_registry(request, response);
            } else {
                response.sendRedirect("Login");
            }
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
