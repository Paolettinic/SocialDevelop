/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.SocialDevelopDataLayer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Task;

/**
 *
 * @author Nicolò Paoletti
 */
public class GetTasksByProject extends SocialDevelopBaseController {

    private void getTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataLayerException, IOException{
        int project_id = request.getParameter("proj_id") == null ? -1 : SecurityLayer.checkNumeric(request.getParameter("proj_id"));
        System.out.println(project_id);
        List<Task> task_list;
        Map<Integer,String> tasks_id_name = new HashMap();
        try{

            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            Progetto p = datalayer.getProgetto(project_id);
            task_list = p.getTasks();
            for(Task t : task_list){
                tasks_id_name.put(t.getKey(), t.getNome());
            }
            System.out.println(tasks_id_name);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print(tasks_id_name);
            }
        }catch(DataLayerException ex){
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            getTasks(request,response);
        } catch (DataLayerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
