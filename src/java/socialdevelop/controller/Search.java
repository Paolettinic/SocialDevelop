
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
import java.util.regex.Matcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;

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
        List<Task> tasks;
        Map<Utente,Integer> collaboratori ;
        List<Integer> k_collaboratori = new ArrayList();
        List<Skill> skills = new ArrayList();
        Map<String,String[]> parameter_get = new HashMap();
        parameter_get.putAll(parameters);
        Map skill_map = new HashMap();
        Map datamodel = new HashMap();
        int project_id, task_id;
        System.out.println("MAP: "+parameter_get);
        
        try{
            
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            HttpSession s = request.getSession(true);
            if (s.getAttribute("userid") == null) {
                request.setAttribute("utente_key", 0);
            } else {
                request.setAttribute("utente_key", (int) s.getAttribute("userid"));
            }
            
            type = parameter_get.containsKey("type") ? SecurityLayer.stripSlashes(parameter_get.remove("type")[0]) : "projects";
            filtro = parameter_get.containsKey("filter") ? SecurityLayer.stripSlashes(parameter_get.remove("filter")[0]) : "";
            page = parameter_get.containsKey("page") ? SecurityLayer.checkNumeric(parameter_get.remove("page")[0]) : 1;
            perPage = parameter_get.containsKey("perpage") ? SecurityLayer.checkNumeric(parameter_get.remove("perpage")[0]) : 8;
            project_id = parameter_get.containsKey("project_select") ? SecurityLayer.checkNumeric(parameter_get.remove("project_select")[0]) : -1;
            task_id = parameter_get.containsKey("task_submit") ? SecurityLayer.checkNumeric(parameter_get.remove("task_submit")[0]) : -1;
            async = parameter_get.containsKey("async") ? SecurityLayer.checkNumeric(parameter_get.remove("async")[0]) == 1 : false;
            
            
            
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
                for(Progetto proj : progetti){
                    tasks = proj.getTasks();
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
                if(s.getAttribute("userid") != null){
                    Utente u = datalayer.getUtente((int)s.getAttribute("userid"));
                    List<Progetto> user_projects = datalayer.getProgetti(u);
                    request.setAttribute("projects", user_projects);
                    datamodel.put("projects", user_projects);
                    if(project_id != -1 && task_id != -1){
                        Task t = datalayer.getTask(task_id);
                        Map<Skill,Integer> map_skill = t.getSkills();
                        for(Map.Entry<Skill,Integer> m_s : map_skill.entrySet()){
                            skill_map.put(m_s.getKey().getKey(), m_s.getValue());
                            
                        }
                        System.out.println("MAP SKILL: "+skill_map);
                    }else{
                        Map<String,String> skill_id = new HashMap();
                        Map<String,String> levels = new HashMap();
                        Pattern p = Pattern.compile("skills_.");
                        Matcher m;
                        for(Map.Entry<String,String[]> set: parameter_get.entrySet()){
                            m = p.matcher(set.getKey());
                            if(m.matches()){
                                skill_id.put(set.getKey().split("skills_")[1], SecurityLayer.stripSlashes(set.getValue()[0]));
                            }
                        }
                        
                        
                        p = Pattern.compile("skill_level_.");
                        for(Map.Entry<String,String[]> set: parameter_get.entrySet()){
                            m = p.matcher(set.getKey());
                            if(m.matches()){
                                levels.put(set.getKey().split("skill_level_")[1], SecurityLayer.stripSlashes(set.getValue()[0]));
                            }
                        }
                        
                        for(Map.Entry<String,String> set: skill_id.entrySet()){
                            
                            if(!set.getValue().equals("-1") && !levels.get(set.getKey()).equals(""))
                                skill_map.put(Integer.parseInt(set.getValue()),Integer.parseInt(levels.get(set.getKey())));
                        }
                    }
                    
                }
                
                
                System.out.println("MAP: "+skill_map);
                
                List<Utente> utenti = datalayer.getUtenti(filtro, skill_map, first, perPage);
                totalResults = datalayer.getCountUtenti(filtro,skill_map);
                request.setAttribute("result", utenti);
                
                datamodel.put("skill_map",skill_map);
                datamodel.put("result", utenti);
                
            }
            
            int totalPages = totalResults/perPage + (totalResults % perPage == 0 ? 0 : 1);
            
            /*
            Se la richiesta avviene tramite una normale GET o POST, viene caricato
            il template normalmente; se invece avviene tramite una chiamata Ajax
            (paginazione) si utilizza il metodo activate per
            scrivere su un file, in modo da poter caricare solo una parte del template
            (senza outline) con i dati aggiornati.
            */
            
            if(!async){
                request.setAttribute("page_title","Search | SocialDevelop");
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
