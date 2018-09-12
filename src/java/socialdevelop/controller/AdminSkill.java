/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Tipo;

/**
 *
 * @author Nicolò Paoletti
 */
public class AdminSkill extends SocialDevelopBaseController{
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }
    
    private void action_default(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
        try{
            HttpSession s = request.getSession(true);
            if(s.getAttribute("userid")==null)
                response.sendRedirect("Index");
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            
            List<Tipo> typeList = datalayer.getTipi();
            List<Skill> skillList = datalayer.getSkills();
            Map datamodel = new HashMap();
            datamodel.put("outline_tpl", "admin_skeleton_page.html");
            datamodel.put("encoding","UTF-8");
            datamodel.put("logged","true");
            datamodel.put("tipi",typeList);
            datamodel.put("page_title","Skill | SocialdevelopAdmin");
            datamodel.put("skills",skillList);
            
            TemplateResult tmp = new TemplateResult(getServletContext());
            tmp.activate("admin_skill.html", datamodel, response);
            
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try{
            action_default(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
