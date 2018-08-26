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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.SocialDevelopDataLayer;
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
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            HttpSession s = request.getSession(true);
            if (s.getAttribute("userid") == null) {
                request.setAttribute("utente_key", 0);
            } else {
                request.setAttribute("utente_key", (int) s.getAttribute("userid"));
            }
            if(s.getAttribute("userid") == null){
                response.sendRedirect("Login");
            }else{
            Utente u = datalayer.getUtente((int) s.getAttribute("userid"));
            Progetto proj = datalayer.getProgetto(project_id);
            List<Progetto> user_projects = u.getProgetti();
            boolean found = false;
            for(Progetto p : user_projects){
                if(p.getKey() == project_id)
                    found = true;
            }
            if(!found)
                response.sendRedirect("Login");
            else{
                request.setAttribute("project_id", project_id);
                request.setAttribute("project", proj);
                request.setAttribute("page_title", "Valuta collaboratori | SocialDevelop");
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("rate.html", request, response);
            }
            }
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
