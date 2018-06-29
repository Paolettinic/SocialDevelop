
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;

/**
 *
 * @author Nicolò Paoletti
 */


public class Search extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        //body for action_error
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, Map<String,String[]> parameters) throws IOException, ServletException, TemplateManagerException{
        int page, first, perPage, totalResults = 0;
        boolean async;
        String filtro;
        String type;
        //List<Map<Skill,Integer>> skills = new ArrayList();
        List<Task> tasks;
        Map<Utente,Integer> collaboratori ;
        Map<Integer,String> test = new HashMap();
        List<Integer> k_collaboratori = new ArrayList();
        List<Skill> skills = new ArrayList();
        List<String> skill_filter = new ArrayList();
        List<String> skill_values = new ArrayList();
        Map skill_map = new HashMap();
        
        
        Map datamodel = new HashMap();
        
        try{
            
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            
            type = parameters.containsKey("type") ? SecurityLayer.stripSlashes(parameters.get("type")[0]) : "projects";
            filtro = parameters.containsKey("filter") ? SecurityLayer.stripSlashes(parameters.get("filter")[0]) : "";
            page = parameters.containsKey("page") ? SecurityLayer.checkNumeric(parameters.get("page")[0]) : 1;
            perPage = parameters.containsKey("perpage") ? SecurityLayer.checkNumeric(parameters.get("perpage")[0]) : 8;
            async = parameters.containsKey("async") ? SecurityLayer.checkNumeric(parameters.get("async")[0]) == 1 : false;
            int i=1;
            System.out.println(parameters.containsKey("skills_1"));
            while(parameters.containsKey("skills_"+i) && parameters.containsKey("skill_level_"+i)){
                System.out.println(i+"--"+"skills_"+i+"--"+SecurityLayer.stripSlashes("skills_"+i));
                skill_map.put(SecurityLayer.checkNumeric(parameters.get("skills_"+i)[0]),SecurityLayer.checkNumeric(parameters.get("skill_level_"+i)[0]));
                i++;
            }
            System.out.println(skill_map);
            first = (page-1)*perPage;
            
            skills = datalayer.getSkills();
            /*
                Ricerca dei progetti
            */
            if(type.equals("projects")){
                List<Progetto> progetti = datalayer.getProgetti(filtro,first,perPage);
                totalResults = datalayer.getCountProgetti(filtro);
                /*
                    Per ogni progetto aggiungo ad un HashMap i collaboratori di ogni 
                    task, in modo da poterli contare senza ripetizione (e.g. un utente 
                    che copre più task in un solo progetto).
                    N.B. l'oggetto usato come key deve implementare i metodi equals e hashCode.
                */
                for(Progetto p : progetti){
                    tasks = p.getTasks();
                    collaboratori = new HashMap();
                    for(Task task : tasks){
                        collaboratori.putAll(task.getUtenti());
                    }
                    k_collaboratori.add(collaboratori.size());
                }
                
                request.setAttribute("result", progetti);
                datamodel.put("result", progetti);
            }
            
            /*
                Ricerca degli sviluppatori
            */
            else{
                Utente u = datalayer.getUtente(1); //Da sostutuire con l'utente della sessione
                List<Progetto> user_projects = datalayer.getProgetti(u);
                List<Utente> utenti = datalayer.getUtenti(filtro, skill_map, first, perPage);
                totalResults = datalayer.getCountUtenti(filtro,skill_map);
                request.setAttribute("result", utenti);
                request.setAttribute("projects", user_projects);
                datamodel.put("skill_map",skill_map);
                datamodel.put("result", utenti);
                datamodel.put("projects", user_projects);
            }
            
            int totalPages = totalResults/perPage + (totalResults % perPage == 0 ? 0 : 1);
            

            /*
                Se la richiesta avviene tramite una normale GET o POST, viene caricato
                il template normalmente; se invece avviene tramite una chiamata Ajax
                (variabile async = true) si utilizza il metodo activate per 
                scrivere su un file, in modo da poter caricare solo una parte del template
                (senza outline) con i dati aggiornati. 
            */
            if(!async){
                request.setAttribute("page",page);
                request.setAttribute("type",type);
                request.setAttribute("perpage",perPage);
                request.setAttribute("filter",filtro);
                request.setAttribute("count_collaboratori",k_collaboratori);
                request.setAttribute("total",totalResults);
                request.setAttribute("totalPages",totalPages);
                request.setAttribute("list_skill",skills);
                request.setAttribute("skill_map",skill_map);
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("search.html", request, response);
            } else {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                
                
                datamodel.put("page",page);
                datamodel.put("filter",filtro);
                datamodel.put("count_collaboratori",k_collaboratori);
                datamodel.put("total",totalResults);
                datamodel.put("totalPages",totalPages);
                datamodel.put("type",type);
                datamodel.put("outline_tpl", null);
                datamodel.put("encoding","UTF-8");
                
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("search.ftl.html", datamodel, bos);
                
                try (PrintWriter out = response.getWriter()) {
                    out.print(bos.toString());
                }
            }
        } catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Map parameters = request.getParameterMap();
        try{
            action_default(request, response, parameters);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
    
}
