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
public class DoAdminSetSkillTypes extends SocialDevelopBaseController {

    private void addSkill(HttpServletRequest request, HttpServletResponse response,int skillId, int[] typeIds) throws ServletException, DataLayerException, IOException{
        try{
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            for(int typeId: typeIds){
                datalayer.salvaAppartenenti(skillId, typeId);
            }
        }catch(DataLayerException ex){
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int skill;
        String[] types_string;
        int[] types;
        try {            
            skill = SecurityLayer.checkNumeric(request.getParameter("skill"));
            types_string = request.getParameterValues("types[]");
            System.out.println(types_string);
            types = new int[types_string.length];
            for(int i=0;i<types_string.length;i++){
                types[i] = SecurityLayer.checkNumeric(types_string[i]);
            }
            addSkill(request,response,skill,types);
        } catch (DataLayerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

}
