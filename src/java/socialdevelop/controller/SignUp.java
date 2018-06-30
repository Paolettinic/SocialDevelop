package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 * @author Mario Vetrini
 */
public class SignUp extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_signup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        request.setAttribute("page_title", "Registrazione iniziale");
        HttpSession s = request.getSession(true);
        if (s.getAttribute("userid") != null && ((int) s.getAttribute("userid")) > 0) {
            response.sendRedirect("Index");
        }
        
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
        
        // IMPOSTO QUESTO ATTRIBUTO A true ANCHE SE LA PAGINA POTRA' ANDARE
        // SULLA SERVLET "SignUpSkills", MA IN QUEL CASO QUEST'ULTIMA LO
        //  SOVRASCRIVERA' CON FALSE
        s.setAttribute("javascript", true);
        
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("signup.html", request, response);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_signup(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException | SQLException | NamingException ex) {
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
        return "Servlet SignUp";
    }
    
}
