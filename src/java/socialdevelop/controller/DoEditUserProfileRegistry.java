package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 * @author Mario Vetrini
 */
public class DoEditUserProfileRegistry extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_edit_user_profile_registry(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        
        // RECUPERO L'UTENTE
        int utente_key = (int) s.getAttribute("userid");
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        
        // SETTO I NUOVI DATI (DATI DA CONTROLLARE, ANCHE LATO CLIENT)
        utente.setNome(request.getParameter("nome"));
        utente.setCognome(request.getParameter("cognome"));
        utente.setUsername(request.getParameter("username"));
        String data_nascita = request.getParameter("data_nascita");
        GregorianCalendar data_nascita_gc = new GregorianCalendar();
        data_nascita_gc.setLenient(false);
        data_nascita_gc.set(GregorianCalendar.YEAR, Integer.valueOf(data_nascita.split("-")[0]));
        data_nascita_gc.set(GregorianCalendar.MONTH, Integer.valueOf(data_nascita.split("-")[1]) - 1); // Bello Java, eh?
        data_nascita_gc.set(GregorianCalendar.DATE, Integer.valueOf(data_nascita.split("-")[2]));
        utente.setDataNascita(data_nascita_gc);
        utente.setBiografia(request.getParameter("biografia"));
        
        // AGGIORNO I DATI DELL'UTENTE
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaUtente(utente);
        
        // RIMANDO L'UTENTE ALLA PAGINA DI MODIFICA DEI DATI (MODALE?)
        response.sendRedirect("EditUserProfileRegistry?utente_key="+utente.getKey());
        
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_edit_user_profile_registry(request, response);
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
