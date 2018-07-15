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
import socialdevelop.data.model.Curriculum;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 * @author Mario Vetrini
 */

@MultipartConfig
public class DoEditUserProfileCV extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_edit_user_profile_cv(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        HttpSession s = request.getSession(true);
        
        // RECUPERO L'UTENTE
        int utente_key = (int) s.getAttribute("userid");
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        
        // RECUPERO GLI IPOTETICI CV DPF E TESTUALI
        Part CV_PDF_Part = request.getPart("CV_PDF");
        String CV_text = request.getParameter("CV_text");
        
        //CONTROLLO SE SONO STATI USATI ENTRAMBI I METODI DI CARICAMENTO
        if (!"".equals(CV_text) && CV_PDF_Part.getSize() > 0) {
            request.setAttribute("errore", "CV caricato in entrambi i modi");
            response.sendRedirect("EditUserProfileCV");
            return;
        } else if (CV_PDF_Part.getSize() == 0 && "".equals(CV_text)) {
            // CONTROLLO SE NON E' STATO CARICATO IL PDF IN ALCUN MODO
            request.setAttribute("errore", "CV non caricato");
            response.sendRedirect("EditUserProfileCV");
            return;
        }
        
        Curriculum curriculum = utente.getCurriculum();
        // CONTROLLO SE E' STATO CARICATO IL PDF TESTUALE E SETTO I CAMPI
        if (!"".equals(CV_text)) {
            // PROVO A CANCELLARE IL VECCHIO CV (SE C'E')
            File file_delete = new File(getServletContext().getRealPath(File.separator)+
                    getServletContext().getInitParameter("cv.directory")+File.separator+
                    utente.getCurriculum().getNome());
            file_delete.delete();
            curriculum.setNome(utente.getUsername().concat("_CV testuale"));
            curriculum.setTipo("curriculum testuale");
            curriculum.setTestuale(CV_text);
        } else {
            // SETTO I CAMPI
            curriculum.setNome(utente.getUsername().concat("_CV.pdf"));
            curriculum.setTipo("curriculum");
            // ELIMINO IL VECCHIO CV DAL SERVER E CARICO IL NUOVO
            File file_delete = new File(getServletContext().getRealPath(File.separator)+
                    getServletContext().getInitParameter("cv.directory")+File.separator+
                    utente.getCurriculum().getNome());
            file_delete.delete();
            // CARICO IL NUOVO
            File file_upload = new File(getServletContext().getRealPath(File.separator)+
                    getServletContext().getInitParameter("cv.directory"), utente.getUsername().concat("_CV.pdf"));
            try (InputStream IS_CV_DPF = CV_PDF_Part.getInputStream();) {
                Files.copy(IS_CV_DPF, file_upload.toPath());
            }
        }
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaCurriculum(curriculum);
        
        // RIMANDO L'UTENTE ALLA PAGINA DI MODIFICA DEI DATI (MODALE?)
        response.sendRedirect("EditUserProfileCV");
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_edit_user_profile_cv(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
