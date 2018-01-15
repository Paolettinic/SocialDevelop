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
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.FileSD;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;

/**
 *
 * @author Nicolò Paoletti
 */
public class Rate extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        return; //body for action_error
    }
       
    private void action_default(HttpServletRequest request, HttpServletResponse response,int project_id)throws IOException, ServletException, TemplateManagerException{
        try{
            Progetto proj = ((SocialDevelopDataLayer)request.getAttribute("datalayer")).getProgetto(project_id);
            List<Task> tasks = proj.getTasks();
            List< List<Utente> > user_tasks = new ArrayList();
            List< List<FileSD> > user_images = new ArrayList();
            List<Utente> user_task;
            List<FileSD> user_image;
            Map<Utente,Integer> usr;
            for( Task t : tasks){
                usr = t.getUtenti();
                user_task = new ArrayList();
                user_image = new ArrayList();
                for(Utente u : usr.keySet()){
                    user_task.add(u);
                    user_image.add(u.getImmagine());
                }
                user_tasks.add(user_task);
                user_images.add(user_image);
            }
            //request.setAttribute("tasks", tasks);
            //request.setAttribute("user_tasks", user_tasks);
            //request.setAttribute("images_users", user_images);
            request.setAttribute("project",proj);
            for(List<Utente> u : user_tasks){
                System.out.println("\n\nTASK");
                for(Utente ut : u){
                    System.out.println("\n"+ut.getNome()+" "+ut.getCognome());
                }
            }
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("rate.html", request, response);
        } catch (DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int project_id;
        try{
            project_id = SecurityLayer.checkNumeric(request.getParameter("proj_id"));
            action_default(request,response,project_id);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
    
    
}
