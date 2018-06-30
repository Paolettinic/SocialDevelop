package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;

/**
 * @author Davide De Marco
 */

public class Project extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        return; //body for action_error
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response,int utente_id, int progetto_id, int page, int perPage)throws IOException, ServletException, TemplateManagerException{
        try {
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            int firstResult = (page-1)*perPage;
            Utente utente = ((SocialDevelopDataLayer)request.getAttribute("datalayer")).getUtente(utente_id);
            Progetto progetto = datalayer.getProgetto(progetto_id);
            Task task = datalayer.getTask(progetto_id);
            List<Task> tasks = datalayer.getTasks(progetto);
            Progetto proj = task.getProgetto();
            Map<Utente,Integer> utenti = datalayer.getUtenti(task);
            List<Discussione> discussioni = datalayer.getDiscussioni(task,firstResult,perPage);
            List<Integer> n_posts = new ArrayList();
            List<Date> deadline = new ArrayList();
            List<Discussione> discussionis = datalayer.getDiscussioni(progetto, firstResult, perPage);
            
            for(Discussione d : discussioni){
                n_posts.add(d.getMessaggi().size());
            }
            
            for(Task t : tasks){
                deadline.add(t.getDataFine().getTime());
            }
            
            request.setAttribute("utente_id", utente_id);
            request.setAttribute("utente", utente);
            request.setAttribute("tasks", tasks);
            request.setAttribute("progetto", progetto);
            request.setAttribute("project_id", proj.getKey());
            request.setAttribute("project_name",proj.getNome());
            request.setAttribute("project_description",proj.getDescrizione());
            request.setAttribute("project_collaborators",proj.getUtente());
            request.setAttribute("utenti",utenti);
            request.setAttribute("progetto",proj);
            request.setAttribute("utente_img",proj.getUtente().getImmagine().toString());
            request.setAttribute("topics",discussioni);
            request.setAttribute("posts",n_posts);
            request.setAttribute("data_fine",task.getDataFine());
            request.setAttribute("deadline", deadline);
            request.setAttribute("topicss", discussionis);
            
            request.setAttribute("currentpage",page);
            request.setAttribute("perPage",perPage);
            
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("project.html", request, response);
        } catch (DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int utente_id;
        int progetto_id;
        int page;
        int perPage;
        try {
            utente_id = SecurityLayer.checkNumeric(request.getParameter("utente_id"));
            progetto_id = SecurityLayer.checkNumeric(request.getParameter("progetto_id"));
            page = request.getParameter("page") == null ? 1 : SecurityLayer.checkNumeric(request.getParameter("page"));
            perPage = request.getParameter("perpage") == null ? 7 : SecurityLayer.checkNumeric(request.getParameter("perpage"));
            action_default(request,response,utente_id, progetto_id, page, perPage);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
}
