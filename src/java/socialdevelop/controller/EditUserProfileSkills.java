package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 * @author Mario Vetrini
 */
public class EditUserProfileSkills extends SocialDevelopBaseController {
    
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
    
    private void action_edit_user_profile_skills(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        request.setAttribute("page_title", "Modifica i tuoi dati");
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente((int) s.getAttribute("userid"));
        if (utente != null) {
            request.setAttribute("profilo_key", (int) s.getAttribute("userid"));
            
            // RECUPERO LE SKILL PER RIEMPIRE LE SELECT
            List<Skill> skills = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkillsNoPadre();
            if (skills != null) {
                for (Skill skill : skills) {
                    List<Skill> figli = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkillsFigli(skill);
                    if (figli != null) {
                        skill.setFigli(figli);
                    }
                }
            }
            request.setAttribute("skills", skills);
            
            request.setAttribute("user_skills", utente.getSkills());
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("edit_user_profile_skills.html", request, response);
        } else {
            response.sendRedirect("Index");
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession s = request.getSession(true);
        try {
            if ((request.getParameter("utente_key") != null) && ((int) s.getAttribute("userid") == Integer.valueOf(request.getParameter("utente_key")))) {
                action_edit_user_profile_skills(request, response);
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
        return "Short description";
    }// </editor-fold>
    
}
