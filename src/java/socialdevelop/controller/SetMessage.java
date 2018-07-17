package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Messaggio;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSession;
import socialdevelop.data.impl.MessaggioImpl;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.Utente;

/**
 * @author Davide De Marco
 */

public class SetMessage extends SocialDevelopBaseController{
    private void setmessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataLayerException, IOException{
        
        HttpSession s = request.getSession(true);
        
        int utente_key = (int) s.getAttribute("userid");
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        
        String testo = request.getParameter("reply");
        int ext_discussione = SecurityLayer.checkNumeric(request.getParameter("discussion_id"));
        GregorianCalendar data = new GregorianCalendar();
        boolean async = false;
        Discussione discussione = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDiscussione(ext_discussione);
        
        if(request.getParameter("async")!=null)
            async = SecurityLayer.checkNumeric(request.getParameter("async")) == 1;
        
        System.out.println(async);
       
        SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
        Messaggio messaggio = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaMessaggio();
        messaggio.setTesto(testo);
        messaggio.setUtente(utente);
        messaggio.setDiscussione(discussione);
        messaggio.setData(data);
        
        datalayer.salvaMessaggio(messaggio);
        System.out.println(testo);
        if (async) {
            System.out.println("async "+testo);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print(testo);
            }
        } else {
            response.sendRedirect("Messages?discussione_id="+ext_discussione);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            setmessage(request, response);
        } catch (DataLayerException | IOException ex) {
            Logger.getLogger(SetMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
