package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.Utente;

public class TaskForum extends SocialDevelopBaseController{
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        return; //body for action_error
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response,int task_id)throws IOException, ServletException, TemplateManagerException{
        try {
            
            Task task = ((SocialDevelopDataLayer)request.getAttribute("datalayer")).getTask(task_id);
            List<Discussione> discussioni = ((SocialDevelopDataLayer)request.getAttribute("datalayer")).getDiscussioni(task);
            Progetto proj = task.getProgetto();
            List<Utente> utenti = new ArrayList();
            List<Date> date_creazione = new ArrayList();
            int[] n_posts = new int[discussioni.size()];
            //String[] imgs = new String[discussioni.size()];
            int[] imgs_id = new int[discussioni.size()];
            
            //Map<Discussione, Utente> discussioni_utenti = new HashMap();
            
            /* for (Iterator<Discussione> it = discussioni.iterator(); it.hasNext();) {
                Discussione d = it.next();
            }*/
            int i = 0;
            for(Discussione d : discussioni){
                utenti.add(d.getUtente());
                date_creazione.add(d.getData().getTime());
                n_posts[i] = d.getMessaggi().size();
                i++;
            }
            
            request.setAttribute("task_name", task.getNome());
            request.setAttribute("project_id", proj.getKey());
            request.setAttribute("project_name",proj.getNome());
            request.setAttribute("topics",discussioni);
            request.setAttribute("posts",n_posts);
            request.setAttribute("date",date_creazione);
            request.setAttribute("users",utenti);
            
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("task_forum.html", request, response);
            
        } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int task_id;
        try{
            task_id = SecurityLayer.checkNumeric(request.getParameter("task_id"));
            action_default(request,response,task_id);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
    
}