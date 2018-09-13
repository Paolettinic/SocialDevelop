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
import java.util.ArrayList;
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

public class DoAddSkills extends SocialDevelopBaseController{
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        System.out.print(request.getAttribute("message")+"\n");
        return; //body for action_error
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, TemplateManagerException{
        try {
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer) request.getAttribute("datalayer"));
            int maxskill = Integer.parseInt(request.getParameter("maxskill"));
            String[] nomi = new String[maxskill];
            int[] voti = new int [maxskill];


            for (int k = 1; k <= maxskill; k++) {
                nomi[k-1] =  request.getParameter("nome_skill_" + k);
                voti[k-1] =  Integer.parseInt(request.getParameter("voto_skill_" + k));
                System.out.println(nomi[k-1] + ", " + voti[k-1]);
            }

            int taskid = SecurityLayer.checkNumeric(request.getParameter("taskiddi"));
            Task t = datalayer.getTask(taskid);
            Progetto p = t.getProgetto();

            for (int k = 0; k < maxskill; k++) {
                int skill_key = datalayer.getSkillByNome(nomi[k]).getKey();
                datalayer.salvaRequisiti(voti[k], skill_key, taskid);
            }

            response.sendRedirect("search?type=developers&project_select="+p.getKey()+"&task_submit="+t.getKey());
        } catch (DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try{
            action_default(request,response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
            
        }
    }
}
