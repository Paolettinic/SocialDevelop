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

public class UserProfile extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_user_profile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        request.setAttribute("utente_key", s.getAttribute("userid"));
        
        Utente utente;
        if (request.getParameter("username") != null) {
            utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteByUsername(request.getParameter("username"));
            if (utente == null) {
                response.sendRedirect("Index");
                return;
            }
        } else {
            utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente((int) s.getAttribute("userid"));
        }
        
        if (utente != null) {
            request.setAttribute("page_title", utente.getUsername());
            request.setAttribute("nome", utente.getNome());
            request.setAttribute("cognome", utente.getCognome());
            request.setAttribute("username", utente.getUsername());
            request.setAttribute("email", utente.getEmail());
            request.setAttribute("biografia", utente.getBiografia());
            request.setAttribute("data_nascita", utente.getDataNascita());
            request.setAttribute("skills", utente.getSkills());
            request.setAttribute("tasks", utente.getTasks());
            request.setAttribute("progetti", utente.getProgetti());
            request.setAttribute("profilo_key", utente.getKey());
            request.setAttribute("propic_key", utente.getImmagine().getKey());
            
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", utente.getUsername());
            res.activate("user_profile.html", request, response);
        } else {
            response.sendRedirect("Index");
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_user_profile(request, response);
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
