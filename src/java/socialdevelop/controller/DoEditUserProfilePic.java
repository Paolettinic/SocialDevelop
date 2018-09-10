package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 * @author Mario Vetrini
 */

@MultipartConfig
public class DoEditUserProfilePic extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_edit_user_profile_pic(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        
        // RECUPERO L'UTENTE
        int utente_key = (int) s.getAttribute("userid");
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        
        // CONTROLLO SE E' STATA CARICATA L'IMMAGINE
        Part Propic_Part = request.getPart("foto_profilo");
        if (Propic_Part == null || Propic_Part.getSize() == 0) {
            request.setAttribute("errore_propic", "Propic non caricata");
            response.sendRedirect("EditUserProfilePic");
            return;
        }
        
        // ELIMINO LA VECCHIA IMMAGINE DEL SERVER E CARICO LA NUOVA
        File file_delete = new File(getServletContext().getRealPath("")+
                getServletContext().getInitParameter("images.directory")+File.separator+
                utente.getImmagine().getTipo()+File.separator+
                utente.getImmagine().getNome());
        if (file_delete.delete()) {
            File file_upload = new File(getServletContext().getRealPath("")+
                getServletContext().getInitParameter("images.directory")+File.separator+
                utente.getImmagine().getTipo(), utente.getImmagine().getNome());
                try (InputStream IS_PROPIC = Propic_Part.getInputStream();) {
                    Files.copy(IS_PROPIC, file_upload.toPath());
                }
        } else {
            // ELIMINAZIONE NON AVVENUTA
        }
        
        // RIMANDO L'UTENTE ALLA PAGINA DI MODIFICA DEI DATI (MODALE?)
        response.sendRedirect("EditUserProfilePic");
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_edit_user_profile_pic(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
