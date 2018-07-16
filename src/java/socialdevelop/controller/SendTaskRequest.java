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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.impl.InvitoImpl;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Invito;
import socialdevelop.data.model.Task;

/**
 *
 * @author Davide De Marco
 */

public class SendTaskRequest extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_send_request(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        
        HttpSession s = request.getSession(true);

        int taskid = SecurityLayer.checkNumeric(request.getParameter("taskiddi"));
        int utente_key = (int) s.getAttribute("userid");
        GregorianCalendar date = new GregorianCalendar();
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(taskid);
        
        Invito invito = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaInvito();
        invito.setUtente(utente);
        invito.setDataInvio(date);
        invito.setTask(task);
        invito.setMessaggio("L'utente "+utente.getNome()+" "+utente.getCognome()+"vorrebbe partecipare al task");
        invito.setOfferta(true);
        invito.setStato("processing");
        
        
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaInvito(invito);
        
        response.sendRedirect("TaskPage?task_id="+task.getKey());
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_send_request(request, response);
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
