package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 * @author Davide De Marco
 */

public class DoDeleteUserTask extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // TODO
    }
    
    private void action_do_delete_user_task(HttpServletRequest request, HttpServletResponse response, int utente_id, int task_id) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).eliminaCoprenti(utente_id, task_id);
        
        response.sendRedirect("TaskPage?task_id="+task_id);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int utente_id;
        int task_id;
        try {
            if (request.getParameter("task_id") != null && request.getParameter("utente_id") != null) {
                utente_id = SecurityLayer.checkNumeric(request.getParameter("utente_id"));
                task_id = SecurityLayer.checkNumeric(request.getParameter("task_id"));
                action_do_delete_user_task(request, response, utente_id, task_id);
            } else {
                action_default(request, response);
            }
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

}
