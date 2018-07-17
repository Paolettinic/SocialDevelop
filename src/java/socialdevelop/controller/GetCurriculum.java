package socialdevelop.controller;

import socialdevelop.data.model.SocialDevelopDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.StreamResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.Curriculum;

/**
 * @author Mario Vetrini
 */

public class GetCurriculum extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
//        // Ad esempio possiamo caricare un'immagine di default se non riusciamo a trovare quella indicata
//        try {
//            action_download(request, response, 0);
//        } catch (IOException | DataLayerException ex) {
//            // Se non possiamo inviare l'immagine di errore, proviamo un messaggio di errore HTTP standard
//            try {
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
//            } catch (IOException ex1) {
//                // Se ANCHE questo stato di errore non può essere notificato, scriviamo sul log del server
//                Logger.getLogger(GetImage.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//        }
    }
    
    private void action_download(HttpServletRequest request, HttpServletResponse response, int cv_key) throws IOException, DataLayerException {
        StreamResult result = new StreamResult(getServletContext());
        Curriculum curriculum = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getCurriculum(cv_key);
        if (curriculum != null) {
            // Settiamo il tipo del file da trasmettere
            request.setAttribute("contentType", "file");
            // Il file non sarà  scaricato, ma mostrato nel browser
            request.setAttribute("contentDisposition", "attachment");
            // Prendiamo il file dal filesystem
            result.activate(
                    new File(
                            getServletContext().getRealPath("/")+
                                    getServletContext().getInitParameter("cv.directory")+File.separator+
                                    curriculum.getNome()
                    ),
                    request,
                    response
            );
        } else {
            throw new DataLayerException("Curriculum non disponibile");
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int cv_key = SecurityLayer.checkNumeric(request.getParameter("cv_key"));
            action_download(request, response, cv_key);
        } catch (NumberFormatException | IOException | DataLayerException ex) {
            action_error(request, response);
        }
    }
    
}
