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
import socialdevelop.data.impl.MessaggioImpl;

/**
 * @author Davide De Marco
 */

public class SetMessage extends SocialDevelopBaseController{
    private void setmessage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, DataLayerException, IOException{
        String testo = request.getParameter("reply");
        int ext_utente = SecurityLayer.checkNumeric(request.getParameter("user_id"));
        int ext_discussione = SecurityLayer.checkNumeric(request.getParameter("discussion_id"));
        GregorianCalendar data = new GregorianCalendar();
        boolean async = false;
        
        if(request.getParameter("async")!=null)
            async = SecurityLayer.checkNumeric(request.getParameter("async")) == 1;
        
        System.out.println(async);
       
        SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
        Messaggio messaggio = new MessaggioImpl(((SocialDevelopDataLayer) request.getAttribute("datalayer")));
        messaggio.setTesto(testo);
        //messaggio.setUtente(utente);
        //messaggio.setDiscussione(discussione);
        
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
            response.sendRedirect("/SocialDevelop/Messages?utente_id="+SecurityLayer.checkNumeric(request.getParameter("user_id"))+"&task_id="+SecurityLayer.checkNumeric(request.getParameter("taskerino_id"))+"&discussione_id="+SecurityLayer.checkNumeric(request.getParameter("taskerino_id")));
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
       String message;
        try {
            message = SecurityLayer.addSlashes(request.getParameter("reply"));
            setmessage(request, response, message);
        } catch (DataLayerException | IOException ex) {
            Logger.getLogger(SetMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
