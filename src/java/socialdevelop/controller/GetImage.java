package socialdevelop.controller;

import socialdevelop.data.model.SocialDevelopDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.StreamResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.Immagine;

/**
 * @author Giuseppe Della Penna
 */

public class GetImage extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        // Ad esempio possiamo caricare un'immagine di default se non riusciamo a trovare quella indicata
        try {
            action_download(request, response, 0);
        } catch (IOException | DataLayerException ex) {
            // Se non possiamo inviare l'immagine di errore, proviamo un messaggio di errore HTTP standard
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            } catch (IOException ex1) {
                // Se ANCHE questo stato di errore non può essere notificato, scriviamo sul log del server
                Logger.getLogger(GetImage.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    private void action_download(HttpServletRequest request, HttpServletResponse response, int imgid) throws IOException, DataLayerException {
        StreamResult result = new StreamResult(getServletContext());
        Immagine image = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getImmagine(imgid);
        if (image != null) {
            // Settiamo il tipo del file da trasmettere
            request.setAttribute("contentType", "image");
            // Il file non sarà  scaricato, ma mostrato nel browser
            request.setAttribute("contentDisposition", "inline");
            // Prendiamo il file dal filesystem
            result.activate(
                new File(
                    getServletContext().getRealPath("/")+
                    getServletContext().getInitParameter("images.directory")+File.separator+
                    image.getTipo()+File.separator+
                    image.getNome()
                ),
                request, 
                response
            );
        } else {
            throw new DataLayerException("Immagine non disponibile");
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            int imgid = SecurityLayer.checkNumeric(request.getParameter("imgid"));
            action_download(request, response, imgid);
        } catch (NumberFormatException | IOException | DataLayerException ex) {
            action_error(request, response);
        }
    }
    
}
