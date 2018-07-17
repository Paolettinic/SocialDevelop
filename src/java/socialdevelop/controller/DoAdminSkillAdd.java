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
import socialdevelop.data.model.Tipo;

/**
 *
 * @author Nicolò Paoletti
 */
public class DoAdminSkillAdd extends SocialDevelopBaseController {

    private void addSkill(HttpServletRequest request, HttpServletResponse response,String newSkillName,int parentSkillId) throws ServletException, DataLayerException, IOException{
        Skill s;
        Skill parent;
        try{
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            s = datalayer.creaSkill();
            s.setNome(newSkillName);
            if(parentSkillId != 0){
               parent = datalayer.getSkill(parentSkillId);
               s.setPadre(parent);
            }
            datalayer.salvaSkill(s);
        }catch(DataLayerException ex){
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String newSkillName;
        int parentSkillId;
        try {            
            newSkillName = SecurityLayer.stripSlashes(request.getParameter("newskill"));
            parentSkillId = SecurityLayer.checkNumeric(request.getParameter("parent"));
            addSkill(request,response,newSkillName,parentSkillId);
        } catch (DataLayerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

}
