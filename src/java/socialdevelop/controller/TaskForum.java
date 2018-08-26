package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    
    private void action_default(HttpServletRequest request, HttpServletResponse response,int task_id, int page, int perPage, boolean async)throws IOException, ServletException, TemplateManagerException{
        try {
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));

            HttpSession s = request.getSession(true);
            if (s.getAttribute("userid") == null) {
                request.setAttribute("utente_key", 0);
            } else {
                request.setAttribute("utente_key", (int) s.getAttribute("userid"));
                request.setAttribute("logged",true);
                
            }
            int firstResult = (page-1)*perPage; //numero del primo risultato della paginazione
            
            Task task = datalayer.getTask(task_id);
            int total = datalayer.getCountDiscussioni(task);
            
            int totalPages = total/perPage + (total % perPage == 0 ? 0 : 1);
            
            List<Discussione> discussioni = datalayer.getDiscussioni(task,firstResult,perPage);
            
            Progetto proj = task.getProgetto();
            List<Utente> utenti = new ArrayList();
            List<Date> date_creazione = new ArrayList();
            List<Integer> n_posts = new ArrayList(); //numero di post per ogni topic
            
            for(Discussione d : discussioni){
                utenti.add(d.getUtente());
                date_creazione.add(d.getData().getTime());
                n_posts.add(d.getMessaggi().size());
            }
            
            /*
                Se la richiesta avviene tramite una normale GET o POST, viene caricato
                il template normalmente; se invece avviene tramite una chiamata Ajax
                (variabile async = true) si utilizza il metodo activate per 
                scrivere su un file, in modo da poter caricare solo una parte del template
                con i dati aggiornati. 
            */
            if(!async){
                
                request.setAttribute("task_name", task.getNome());
                request.setAttribute("task_id", task.getKey());
                request.setAttribute("project_id", proj.getKey());
                request.setAttribute("project_name",proj.getNome());
                request.setAttribute("topics",discussioni);
                request.setAttribute("posts",n_posts);
                request.setAttribute("date",date_creazione);
                request.setAttribute("users",utenti);
                request.setAttribute("page_title","SocialDevelop | Forum");
                request.setAttribute("currentpage",page);
                request.setAttribute("perPage",perPage);
                request.setAttribute("totalResults",total);
                request.setAttribute("totalPages",totalPages);
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("task_forum.html", request, response);
            }
            else{
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Map datamodel = new HashMap();
                datamodel.put("topics",discussioni);
                datamodel.put("task_id", task.getKey());
                datamodel.put("posts",n_posts);
                datamodel.put("date",date_creazione);
                datamodel.put("users",utenti);
                datamodel.put("outline_tpl", null);
                datamodel.put("encoding","UTF-8");
                datamodel.put("currentpage",page);
                datamodel.put("perPage",perPage);
                datamodel.put("totalResults",total);
                datamodel.put("totalPages",totalPages);
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("task_discussions.ftl.html", datamodel, bos);
                
                try (PrintWriter out = response.getWriter()) {
                    out.print(bos.toString());
                }
            }
        } 
        catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int task_id;
        int page;
        int perPage;
        boolean async;
        try{
            page = request.getParameter("page") == null ? 1 : SecurityLayer.checkNumeric(request.getParameter("page"));
            perPage = request.getParameter("perpage") == null ? 10 : SecurityLayer.checkNumeric(request.getParameter("perpage"));
            async = request.getParameter("async") == null ?  false : SecurityLayer.checkNumeric(request.getParameter("async")) == 1 ;
            task_id = SecurityLayer.checkNumeric(request.getParameter("task_id"));
            action_default(request,response,task_id,page,perPage,async);
        } 
        catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);   
        } 
        catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
    
}