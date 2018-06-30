package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.impl.UtenteImpl;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 * @author Mario Vetrini
 */
public class SignUpFiles extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }
    
    private void action_signup_files(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        request.setAttribute("page_title", "Registrazione - CV e Propic");
        HttpSession s = request.getSession(true);
        if (s.getAttribute("userid") != null && ((int) s.getAttribute("userid")) > 0) {
            response.sendRedirect("Index");
        }
        
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
        
        UtenteImpl utente = new UtenteImpl(((SocialDevelopDataLayer) request.getAttribute("datalayer")));
        
        // CONTROLLO SE SONO ARRIVATO A QUESTA PAGINA DA "SignUpSkills" o "SignUp"
        if(s.getAttribute("javascript").equals(false)) {
            s.removeAttribute("javascript");
            utente = (UtenteImpl) s.getAttribute("utente");
        } else {
            
            // RACCOLGO I DATI GENERALI (DATI DA CONTROLLARE)
            utente.setNome(request.getParameter("nome"));
            utente.setCognome(request.getParameter("cognome"));
            
            // CONTROLLO SE LO USERNAME E' GIA' UTILIZZATO (DATO DA CONTROLLARE)
            utente.setUsername(request.getParameter("username"));
            if (((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteByUsername(request.getParameter("username")) != null) {
                request.setAttribute("errore_signup", "Username utilizzato");
                response.sendRedirect("SignUp");
            }
            
            // CONTROLLO SE LA E-MAIL E' GIA' UTILIZZATA (DATO DA CONTROLLARE)
            utente.setEmail(request.getParameter("email"));
            if (((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteByEmail(request.getParameter("email")) != null) {
                request.setAttribute("errore_signup", "E-mail utilizzata");
                response.sendRedirect("SignUp");
            }
            
            String password_MD5 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).GeneraPasswordMD5(request.getParameter("password"));
            utente.setPassword(password_MD5);
            String data_nascita = request.getParameter("data_nascita");
            GregorianCalendar data_nascita_gc = new GregorianCalendar();
            data_nascita_gc.setLenient(false);
            data_nascita_gc.set(GregorianCalendar.YEAR, Integer.valueOf(data_nascita.split("-")[0]));
            data_nascita_gc.set(GregorianCalendar.MONTH, Integer.valueOf(data_nascita.split("-")[1]) - 1); // Bello Java, eh?
            data_nascita_gc.set(GregorianCalendar.DATE, Integer.valueOf(data_nascita.split("-")[2]));
            utente.setDataNascita(data_nascita_gc);
            utente.setBiografia(request.getParameter("biografia"));
        }

        // METTO L'UTENTE, I NOMI DELLE SKILLS E I RELATIVI VOTI DELLA SESSIONE
        s.setAttribute("utente", utente);
        s.setAttribute("nomi_skill", nomi_skill);
        s.setAttribute("voti_skill", voti_skill);
        
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("signupfiles.html", request, response);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_signup_files(request, response);
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
