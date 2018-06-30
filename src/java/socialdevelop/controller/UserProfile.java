package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.SplitSlashesFmkExt;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
//        try {
//            TemplateResult res = new TemplateResult(getServletContext());
//            //aggiungiamo al template un wrapper che ci permette di chiamare la funzione stripSlashes
//            //add to the template a wrapper object that allows to call the stripslashes function
//            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
//            res.activate("write_list.ftl.html", request, response);
//        } catch (DataLayerException ex) {
//            request.setAttribute("message", "Data access exception: " + ex.getMessage());
//            action_error(request, response);
//        }
    }
    
    private void action_user_profile(HttpServletRequest request, HttpServletResponse response, int utente_key) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        if (utente != null) {
            request.setAttribute("page_title", utente.getUsername());
            //request.setAttribute("utente_key", utente_key);
            request.setAttribute("nome", utente.getNome());
            request.setAttribute("cognome", utente.getCognome());
            request.setAttribute("username", utente.getUsername());
            request.setAttribute("email", utente.getEmail());
            request.setAttribute("biografia", utente.getBiografia());
            request.setAttribute("data_nascita", utente.getDataNascita());
            request.setAttribute("skills", utente.getSkills());
            request.setAttribute("tasks", utente.getTasks());
            request.setAttribute("progetti", utente.getProgetti());
            request.setAttribute("utente_key", s.getAttribute("userid"));
            request.setAttribute("profilo_key", utente_key);
            //getImg(request, response, utente);
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("user_profile.html", request, response);
        } else {
            response.sendRedirect("Index");
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int utente_key;
        try {
            if (request.getParameter("utente_key") != null) {
                utente_key = SecurityLayer.checkNumeric(request.getParameter("utente_key"));
                action_user_profile(request, response, utente_key);
            } else {
                action_default(request, response);
            }
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
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet ProfiloUtente";
    }
    
}
