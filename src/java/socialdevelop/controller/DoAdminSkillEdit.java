/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 *
 * @author Nicolò Paoletti
 */
public class DoAdminSkillEdit extends SocialDevelopBaseController {

    private void editSkill(HttpServletRequest request, HttpServletResponse response,String newName, int new_parent,int skill_id) throws ServletException, DataLayerException, IOException{
        Skill s, parent;
        try{
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            s = datalayer.getSkill(skill_id);
            if(new_parent != 0){
                parent = datalayer.getSkill(new_parent);
                s.setPadre(parent);
            }
            s.setNome(newName);
            datalayer.salvaSkill(s);
            response.sendRedirect("type");
        }catch(DataLayerException ex){
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String newName;
        int skillid, parentid;
        try {            
            newName=SecurityLayer.stripSlashes(request.getParameter("newname"));
            skillid=SecurityLayer.checkNumeric(request.getParameter("skill_id"));
            parentid=SecurityLayer.checkNumeric(request.getParameter("newparent"));
            editSkill(request,response,newName,parentid,skillid);
        } catch (DataLayerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

}
