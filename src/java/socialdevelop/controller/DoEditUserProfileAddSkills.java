package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
public class DoEditUserProfileAddSkills extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_edit_user_profile_add_skills(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        int utente_key = (int) s.getAttribute("userid");
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente((int) s.getAttribute("userid"));
        
        // NOMI E RELATIVI VOTI DEGLI SKILL
        int i = 1;
        ArrayList<String> nomi_skill = new ArrayList<>();
        Set<String> nomi_skill_check =  new HashSet<>();
        ArrayList<Integer> voti_skill = new ArrayList<>();
        
        while (request.getParameter("nome_skill_".concat(String.valueOf(i))) != null) {
            nomi_skill.add(request.getParameter("nome_skill_".concat(String.valueOf(i))));
            nomi_skill_check.add(request.getParameter("nome_skill_".concat(String.valueOf(i))));
            voti_skill.add(Integer.valueOf(request.getParameter("voto_skill_".concat(String.valueOf(i)))));
            i++;
        }
        
        // CONTROLLO SE SONO STATI RIPETUTI DEGLI SKILL
        if (nomi_skill_check.size() != nomi_skill.size()) {
            request.setAttribute("errore_signup", "Skill ripetute");
            response.sendRedirect("SignUp");
        }
        
        // PRENDO LE KEY DELLE SKILLS E CONTROLLO SE NON SIANO GIA' PRESENTI SULL'UTENTE
        for (int k = 0; k < nomi_skill.size(); k++) {
            int skill_key = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkillByNome(nomi_skill.get(k)).getKey();
            
            //((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaPreparazioni(voti_skill.get(k), utente_key, skill_key);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_edit_user_profile_add_skills(request, response);
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
