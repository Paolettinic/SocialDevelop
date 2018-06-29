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
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import java.util.Map;
import socialdevelop.data.model.Discussione;

/**
 *
 * @author Davide
 */

    public class TaskPage extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        return; //body for action_error
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response,int utente_id, int task_id, int page, int perPage)throws IOException, ServletException, TemplateManagerException{
        try{
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            int firstResult = (page-1)*perPage;
            Utente utente = ((SocialDevelopDataLayer)request.getAttribute("datalayer")).getUtente(utente_id);
            Task task = datalayer.getTask(task_id);
            Progetto proj = task.getProgetto();
            Map<Utente,Integer> utenti = datalayer.getUtenti(task);
            List<Discussione> discussioni = datalayer.getDiscussioni(task,firstResult,perPage);
            List<Integer> n_posts = new ArrayList();
            System.out.println(utenti);
            
            request.setAttribute("utente_id", utente_id);
            request.setAttribute("utente", utente);
            request.setAttribute("task", task);
            request.setAttribute("task_name", task.getNome());
            request.setAttribute("task_id", task.getKey());
            request.setAttribute("project_id", proj.getKey());
            request.setAttribute("project_name",proj.getNome());
            request.setAttribute("project_description",proj.getDescrizione());
            request.setAttribute("project_collaborators",proj.getUtente());
            request.setAttribute("data_fine",task.getDataFine());
            request.setAttribute("utenti",utenti);
            request.setAttribute("progetto",proj);
            request.setAttribute("utente_img",proj.getUtente().getImmagine().toString());
            request.setAttribute("topics",discussioni);
            request.setAttribute("posts",n_posts);
            
            request.setAttribute("currentpage",page);
            request.setAttribute("perPage",perPage);
            
            for(Discussione d : discussioni){
                n_posts.add(d.getMessaggi().size());
            }
            
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("task.html", request, response);
        } catch (DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int utente_id;
        int task_id;
        int page;
        int perPage;
        try{
            utente_id = SecurityLayer.checkNumeric(request.getParameter("utente_id"));
            task_id = SecurityLayer.checkNumeric(request.getParameter("task_id"));
            page = request.getParameter("page") == null ? 1 : SecurityLayer.checkNumeric(request.getParameter("page"));
            perPage = request.getParameter("perpage") == null ? 3 : SecurityLayer.checkNumeric(request.getParameter("perpage"));
            action_default(request,response,utente_id, task_id, page, perPage);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
    
    
}

