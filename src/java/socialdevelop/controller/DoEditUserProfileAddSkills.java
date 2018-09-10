package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
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
        
        // RECUPERO LE SKILL DALLA FORM
        while (request.getParameter("nome_skill_".concat(String.valueOf(i))) != null) {
            nomi_skill.add(request.getParameter("nome_skill_".concat(String.valueOf(i))));
            nomi_skill_check.add(request.getParameter("nome_skill_".concat(String.valueOf(i))));
            voti_skill.add(Integer.valueOf(request.getParameter("voto_skill_".concat(String.valueOf(i)))));
            i++;
        }
        
        // CONTROLLO SE SONO STATI RIPETUTI DEGLI SKILL
        if (nomi_skill_check.size() != nomi_skill.size()) {
            response.sendRedirect("EditUserProfileSkills");
            return;
        }
        
        // PRENDO LE SKILLS E CONTROLLO CHE NON SIANO GIA' PRESENTI SULL'UTENTE
        for (int k = 0; k < nomi_skill.size(); k++) {
            if (utente.getSkills().containsKey(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkillByNome(nomi_skill.get(k)))) {
                response.sendRedirect("EditUserProfileSkills");
                return;
            }
        }
        
        // SALVO LE NUOVE SKILLS
        for (int k = 0; k < nomi_skill.size(); k++) {
            int skill_key = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkillByNome(nomi_skill.get(k)).getKey();
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaPreparazioni(voti_skill.get(k), utente_key, skill_key);
        }
        
        // RIMANDO ALLA PAGINA PRECEDENTE
        response.sendRedirect("EditUserProfileSkills");
        
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
    
}
