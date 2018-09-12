/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;

/**
 *
 * @author Davide De Marco
 */
public class DoEditTask extends SocialDevelopBaseController{
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_edit_task(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        
        String nome = request.getParameter("nome");
        String open = request.getParameter("open");
        String max = request.getParameter("maxcollab");
        int taskid = SecurityLayer.checkNumeric(request.getParameter("taskid"));
        String deadline = request.getParameter("deadline");
        
        GregorianCalendar dl = new GregorianCalendar();
        dl.setLenient(false);
        dl.set(GregorianCalendar.YEAR, Integer.valueOf(deadline.split("-")[0]));
        dl.set(GregorianCalendar.MONTH, Integer.valueOf(deadline.split("-")[1]) - 1); // Bello Java, eh?
        dl.set(GregorianCalendar.DATE, Integer.valueOf(deadline.split("-")[2]));
        
        boolean aperto;
        if (open.equals("0")) {
            aperto = false;
        } else {
            aperto = true;
        }
        
        int maxcoll = Integer.parseInt(max);
        
        Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(taskid);
        task.setChiuso(aperto);
        task.setNome(nome);
        task.setNumeroMassimoCollaboratori(maxcoll);
        task.setDataFine(dl);
        
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaTask(task);
        
        response.sendRedirect("TaskPage?task_id="+task.getKey());
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_edit_task(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
