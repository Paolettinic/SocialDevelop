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
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.Invito;
import socialdevelop.data.model.Skill;

/**
 *
 * @author Davide De Marco
 */

    public class TaskPage extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        return; //body for action_error
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, int task_id)throws IOException, ServletException, TemplateManagerException{
        try{
            
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            
            HttpSession s = request.getSession(true);
            
            if (s.getAttribute("userid") == null) {
                request.setAttribute("utente_key", 0);
            } else {
                request.setAttribute("utente_key", (int) s.getAttribute("userid"));
            }
            
            Task task = datalayer.getTask(task_id);
            Progetto proj = task.getProgetto();
            int id_coordinatore = proj.getUtente().getKey();
            List<Invito> inviti = datalayer.getInviti(task);
            Map<Utente,Integer> utenti = datalayer.getUtenti(task);
            List<Discussione> discussioni = datalayer.getDiscussioni(task,0,3);
            List<Integer> n_posts = new ArrayList();
            
            Map<Skill, Integer> skills = datalayer.getSkills(task);
            
            if( s.getAttribute("userid")!=null){
                int utente_key = (int) s.getAttribute("userid");
                Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
                boolean skilled = datalayer.checkUtenteTask(utente, task);
                
                request.setAttribute("skilled", skilled);
                request.setAttribute("utente", utente);
            }
            
            
            
            request.setAttribute("coordinatore", id_coordinatore);
            request.setAttribute("skills", skills);
            
            request.setAttribute("inviti", inviti);
            
            request.setAttribute("task", task);
            request.setAttribute("task_name", task.getNome());
            request.setAttribute("page_title", task.getNome());
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
        int task_id;
        try{
            task_id = SecurityLayer.checkNumeric(request.getParameter("task_id"));
            action_default(request,response, task_id);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
    
    
}