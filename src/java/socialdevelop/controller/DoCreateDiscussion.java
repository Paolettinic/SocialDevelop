/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Invito;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.Task;

/**
 *
 * @author Davide De Marco
 */
public class DoCreateDiscussion extends SocialDevelopBaseController{
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_create_discussion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        
        HttpSession s = request.getSession(true);

        int utente_key = (int) s.getAttribute("userid");
        String titolo = request.getParameter("titolo");
        String pubblica = request.getParameter("pubblica");
        String testo = request.getParameter("testo");
        int taskid = SecurityLayer.checkNumeric(request.getParameter("taskerino_id"));
        Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(taskid);
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        GregorianCalendar time = new GregorianCalendar();

        boolean pubbblica;
        if (pubblica.equals("true")) {
            pubbblica = true;
        } else {
            pubbblica = false;
        }
            
        Discussione discussione = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaDiscussione();
        Messaggio messaggio = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaMessaggio();
        discussione.setUtente(utente);
        discussione.setTitolo(titolo);
        discussione.setPubblica(pubbblica);
        discussione.setData(time);
        discussione.setTask(task);
        
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaDiscussione(discussione);
        
        messaggio.setData(time);
        messaggio.setUtente(utente);
        messaggio.setDiscussione(discussione);
        messaggio.setTesto(testo);
        
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaMessaggio(messaggio);
        
        response.sendRedirect("TaskForum?task_id="+taskid+"&page=1&perPage=1");
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_create_discussion(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
