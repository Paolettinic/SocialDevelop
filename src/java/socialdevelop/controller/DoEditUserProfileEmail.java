package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 *
 * @author Mario
 */
public class DoEditUserProfileEmail extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_edit_user_profile_email(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        
        // RECUPERO L'UTENTE
        int utente_key = (int) s.getAttribute("userid");
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        
        // EFFETTUO VARI CONTROLLI SUI DATI (LATO CLIENT CONTROLLARE CHE LE DUE NUOVE PASSWORD SIANO UGUALI E DIVERSE DALLA VECCHIA)
        String new_email = request.getParameter("new_email");
        if (new_email.equals(utente.getEmail())) {
            // RIMANDARE ALLA STESSA PAGINA MA CON UN MESSAGGIO DI ERRORE
        }
        
        // SETTO LA NUOVA E-MAIL
        utente.setEmail(new_email);
        
        // AGGIORNO I DATI DELL'UTENTE
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaUtente(utente);
        
        // RIMANDO L'UTENTE ALLA PAGINA DI MODIFICA DEI DATI (MODALE?)
        response.sendRedirect("EditUserProfileEmail?utente_key="+utente.getKey());
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_edit_user_profile_email(request, response);
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
