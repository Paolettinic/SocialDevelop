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
import socialdevelop.data.impl.TipoImpl;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Tipo;

/**
 *
 * @author Nicolò Paoletti
 */
public class DoAdminTypeAdd extends SocialDevelopBaseController {

    private void addType(HttpServletRequest request, HttpServletResponse response,String newTypeName) throws ServletException, DataLayerException, IOException{
        Tipo t;
        try{
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            t= datalayer.creaTipo();
            t.setNome(newTypeName);
            datalayer.salvaTipo(t);
        }catch(DataLayerException ex){
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String newTypeName;
        try {            
            newTypeName=SecurityLayer.stripSlashes(request.getParameter("newtypename"));
            addType(request,response,newTypeName);
        } catch (DataLayerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

}
