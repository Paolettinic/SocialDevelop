package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 * @author Mario Vetrini
 */
public class DoEditUserProfileEditSkill extends SocialDevelopBaseController {
    
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
    
    private void action_do_edit_user_profile_edit_skill(HttpServletRequest request, HttpServletResponse response, int skill_key) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        int utente_key = (int) s.getAttribute("userid");
        int voto_skill = Integer.valueOf(request.getParameter("voto_skill"));
        
        // MODIFICO IL VOTO DELLA SKILL DELL'UTENTE
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaPreparazioni(voto_skill, utente_key, skill_key);
        
        // RIMANDO L'UTENTE ALLA PAGINA DI MODIFICA DELLE SKILLS (MODALE?)
        response.sendRedirect("EditUserProfileSkills?utente_key="+utente_key);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int skill_key;
        try {
            if (request.getParameter("skill_key") != null) {
                skill_key = SecurityLayer.checkNumeric(request.getParameter("skill_key"));
                action_do_edit_user_profile_edit_skill(request, response, skill_key);
            } else {
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
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
