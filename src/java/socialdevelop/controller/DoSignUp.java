package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import socialdevelop.data.impl.UtenteImpl;
import socialdevelop.data.model.Curriculum;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Immagine;

/**
 * @author Mario Vetrini
 */

@MultipartConfig
public class DoSignUp extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_dosignup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        HttpSession s = request.getSession(true);
        if (s.getAttribute("userid") != null && ((int) s.getAttribute("userid")) > 0) {
            response.sendRedirect("Index");
            return;
        }
        
        // RECUPERO L'UTENTE, I SUOI SKILL E I RELATIVI VOTI DALLA PAGINA PRECEDENTE
        UtenteImpl utente = (UtenteImpl) s.getAttribute("utente");
        ArrayList<String> nomi_skill = (ArrayList<String>) s.getAttribute("nomi_skill");
        ArrayList<Integer> voti_skill = (ArrayList<Integer>) s.getAttribute("voti_skill");
        
        // RECUPERO GLI IPOTETICI CV DPF E TESTUALI
        Part CV_PDF_Part = request.getPart("CV_PDF");
        String CV_text = request.getParameter("CV_text");
        
        //CONTROLLO SE SONO STATI USATI ENTRAMBI I METODI DI CARICAMENTO
        if (!"".equals(CV_text) && CV_PDF_Part.getSize() > 0) {
            request.setAttribute("errore", "CV caricato in entrambi i modi");
            response.sendRedirect("SignUpFiles");
            return;
        } else if (CV_PDF_Part.getSize() == 0 && "".equals(CV_text)) {
            // CONTROLLO SE NON E' STATO CARICATO IL PDF IN ALCUN MODO
            request.setAttribute("errore", "CV non caricato");
            response.sendRedirect("SignUpFiles");
            return;
        }
        
        Curriculum curriculum = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaCurriculum();
        // CONTROLLO SE E' STATO CARICATO IL PDF TESTUALE E SETTO I CAMPI
        if (!"".equals(CV_text)) {
            curriculum.setNome(utente.getUsername().concat("_CV testuale"));
            curriculum.setTipo("curriculum testuale");
            curriculum.setTestuale(CV_text);
        } else {
            // CARICO IL PDF SUL SERVER
            curriculum.setNome(utente.getUsername().concat("_CV.pdf"));
            curriculum.setTipo("curriculum");
            File file = new File(getServletContext().getRealPath("")+
                getServletContext().getInitParameter("cv.directory"), utente.getUsername().concat("_CV.pdf"));
            try (InputStream IS_CV_DPF = CV_PDF_Part.getInputStream();) {
                Files.copy(IS_CV_DPF, file.toPath());
            }
        }
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaCurriculum(curriculum);
        utente.setCurriculum(curriculum);
        
        
        // CONTROLLO SE E' STATA CARICATA L'IMMAGINE
        Part Propic_Part = request.getPart("foto_profilo");
        if (Propic_Part == null || Propic_Part.getSize() == 0) {
            request.setAttribute("errore", "Propic non caricata");
            response.sendRedirect("SignUpFiles");
            return;
        }
        // CARICO L'IMMAGINE
        Immagine immagine = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaImmagine();
        immagine.setNome(utente.getUsername().concat("_PROPIC.png"));
        immagine.setTipo("members-img");
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaImmagine(immagine);
        utente.setImmagine(immagine);
        //File file = new File("/Users/Mario/Desktop/prova", utente.getUsername().concat("_PROPIC.png"));
        File file = new File(getServletContext().getRealPath("")+
                getServletContext().getInitParameter("images.directory")+File.separator+
                utente.getImmagine().getTipo(), utente.getUsername().concat("_PROPIC.png"));
        try (InputStream IS_PROPIC = Propic_Part.getInputStream();) {
            Files.copy(IS_PROPIC, file.toPath());
        }
        
        // INSERISCO L'UTENTE
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaUtente(utente);
        
        // PRENDO LE KEY DEGLI SKILL E INSERISCO LE TUPLE IN "PREPARAZIONI"
        for (int k = 0; k < nomi_skill.size(); k++) {
            int skill_key = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkillByNome(nomi_skill.get(k)).getKey();
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaPreparazioni(voti_skill.get(k), utente.getKey(), skill_key);
        }
        
        // PULISCO LA SESSIONE
        s.removeAttribute("utente");
        s.removeAttribute("nomi_skill");
        s.removeAttribute("voti_skill");
        
        // CREO LA SESSIONE DELL'UTENTE
        SecurityLayer.createSession(request, utente.getUsername(), utente.getKey());
        
        // MANDO L'UTENTE ALLA SUA PAGINA DEL PROFILO
        response.sendRedirect("UserProfile?username="+utente.getUsername());
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_dosignup(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException | SQLException | NamingException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
