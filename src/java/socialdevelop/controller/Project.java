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
import javax.servlet.http.HttpSession;
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
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, int progetto_id)throws IOException, ServletException, TemplateManagerException{
        try {
            
            HttpSession s = request.getSession(true);
            
            if (s.getAttribute("userid") == null) {
                request.setAttribute("utente_key", 0);
            } else {
                request.setAttribute("utente_key", (int) s.getAttribute("userid"));
            }
            
            int utente_key = (int) s.getAttribute("userid");
            
            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
            
            
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            Progetto progetto = datalayer.getProgetto(progetto_id);
            List<Task> tasks = datalayer.getTasks(progetto);
            
            List<Integer> n_posts = new ArrayList();
            List<Date> deadline = new ArrayList();
            List<Discussione> discussionis = datalayer.getDiscussioni(progetto, 0, 3);
            List<Discussione> discussioni = new ArrayList<>();
            
            for(Task t : tasks){
                deadline.add(t.getDataFine().getTime());
                discussioni.addAll(datalayer.getDiscussioni(t,0,3));
            }
            
            for(Discussione d : discussioni){
                n_posts.add(d.getMessaggi().size());
            }
            
            request.setAttribute("utente", utente);
            request.setAttribute("tasks", tasks);
            request.setAttribute("progetto", progetto);
            request.setAttribute("project_id", progetto.getKey());
            request.setAttribute("project_name",progetto.getNome());
            request.setAttribute("project_description",progetto.getDescrizione());
            request.setAttribute("project_collaborators",progetto.getUtente());
            request.setAttribute("progetto",progetto);
            request.setAttribute("utente_img",progetto.getUtente().getImmagine().toString());
            request.setAttribute("topics",discussioni);
            request.setAttribute("posts",n_posts);
            request.setAttribute("deadline", deadline);
            request.setAttribute("topicss", discussionis);
            request.setAttribute("utente_key", s.getAttribute("userid"));
            
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("project.html", request, response);
        } catch (DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int progetto_id;
        try {
            progetto_id = SecurityLayer.checkNumeric(request.getParameter("progetto_id"));
            action_default(request,response, progetto_id);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
}
