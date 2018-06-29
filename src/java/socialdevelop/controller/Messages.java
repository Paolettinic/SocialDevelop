/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import java.util.Map;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.Messaggio;

/**
 *
 * @author Davide De Marco
 */


public class Messages extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        return; //body for action_error
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response,int utente_id, int task_id, int discussione_id)throws IOException, ServletException, TemplateManagerException{
        try{
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            Utente utente = ((SocialDevelopDataLayer)request.getAttribute("datalayer")).getUtente(utente_id);
            Task task = datalayer.getTask(task_id);
            Discussione discussione = datalayer.getDiscussione(discussione_id);
            List<Messaggio> messaggi = datalayer.getMessaggi(discussione);
            Map<Utente,Integer> utenti = datalayer.getUtenti(task);
            System.out.println(utenti);
            
            request.setAttribute("utente_id", utente_id);
            request.setAttribute("utente", utente);
            request.setAttribute("task", task);
            request.setAttribute("task_name", task.getNome());
            request.setAttribute("task_id", task.getKey());
            request.setAttribute("data_fine",task.getDataFine());
            request.setAttribute("utenti",utenti);
            request.setAttribute("discussione",discussione);
            request.setAttribute("messaggi",messaggi);
            //request.setAttribute("currentpage",page);
            //request.setAttribute("perPage",perPage);
            
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("topic_replies.html", request, response);
        } catch (DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int utente_id;
        int task_id;
        int discussione_id;
        //int page;
        //int perPage;
        try{
            utente_id = SecurityLayer.checkNumeric(request.getParameter("utente_id"));
            task_id = SecurityLayer.checkNumeric(request.getParameter("task_id"));
            discussione_id = SecurityLayer.checkNumeric(request.getParameter("discussione_id"));
            //page = request.getParameter("page") == null ? 1 : SecurityLayer.checkNumeric(request.getParameter("page"));
            //perPage = request.getParameter("perpage") == null ? 10 : SecurityLayer.checkNumeric(request.getParameter("perpage"));
            action_default(request,response,utente_id, task_id, discussione_id);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
}
