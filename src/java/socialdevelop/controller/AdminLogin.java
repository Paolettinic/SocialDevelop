/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolò Paoletti
 */
public class AdminLogin extends SocialDevelopBaseController {

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
        
        Map datamodel = new HashMap();
        
        datamodel.put("outline_tpl", "admin_skeleton_page.html");
        datamodel.put("encoding","UTF-8");
        datamodel.put("page_title","Admin");
        TemplateResult tmp = new TemplateResult(getServletContext());
        tmp.activate("admin_login.html", datamodel, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            showLoginPage(request,response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);    
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
