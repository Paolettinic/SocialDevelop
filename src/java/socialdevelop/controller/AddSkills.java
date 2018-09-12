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
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Tipo;
import socialdevelop.data.model.Utente;

/**
 *
 * @author Davide De Marco
 */

public class AddSkills extends SocialDevelopBaseController{
    
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
            
            int utente_key = (int) s.getAttribute("userid");
        
            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(task_id);
            Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(task.getTipo().getKey());
            List<Skill> skills = datalayer.getSkills(tipo);
            
            request.setAttribute("skills", skills);
            request.setAttribute("task", task);
            request.setAttribute("utente_key", s.getAttribute("userid"));
            request.setAttribute("page_title", task.getNome());
            request.setAttribute("utente", utente);
            
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("add_skills.html", request, response);
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

