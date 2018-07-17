package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.io.IOException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 * @author Mario Vetrini
 */

public class SignUpSkills extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }
    
    private void action_signup_skills(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        request.setAttribute("page_title", "Registrazione - Skills");
        HttpSession s = request.getSession(true);
        
        if (s.getAttribute("userid") != null) {
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
        
        // RECUPERO IL NUMERO DI SKILL CHE L'UTENTE VUOLE INSERIRE
        int numero_skill = Integer.valueOf(request.getParameter("numero_skill"));
        request.setAttribute("numero_skill", numero_skill);
        
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaUtente();
        
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
        
        // METTO L'UTENTE DA REGISTRARE NELLA SESSIONE,
        // IN MODO DA RECUPERARLO NELLA PROSSIMA PAGINA
        s.setAttribute("utente", utente);
        
        // COMUNICO ALLA PAGINA SUCCESSIVA CHE ARRIVO
        // DALLA PAGINA CON JAVASCRIPT DISATTIVATO
        s.setAttribute("javascript", false);
        
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("signupskills.html", request, response);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_signup_skills(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException | SQLException | NamingException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
