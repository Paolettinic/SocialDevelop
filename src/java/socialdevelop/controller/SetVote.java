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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;

/**
 *
 * @author Nicolò Paoletti
 */




public class SetVote extends SocialDevelopBaseController {
    
    private void setvote(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataLayerException, IOException{
        boolean edit = request.getParameter("edit") == null ? false : SecurityLayer.checkNumeric(request.getParameter("edit")) == 1;
        int voto = request.getParameter("vote") == null ? 0 : SecurityLayer.checkNumeric(request.getParameter("vote"));
        int ext_utente = SecurityLayer.checkNumeric(request.getParameter("user_id"));
        int ext_task = SecurityLayer.checkNumeric(request.getParameter("task_id"));
        boolean async = false;
        
        if(request.getParameter("async")!=null)
            async = SecurityLayer.checkNumeric(request.getParameter("async")) == 1;
        
        System.out.println(async);
        
        try{
            SocialDevelopDataLayer datalayer = ((SocialDevelopDataLayer)request.getAttribute("datalayer"));
            HttpSession s = request.getSession(true);
            int logged_id;
            if (s.getAttribute("userid") == null) {
                response.sendRedirect("Index");
            }else{
                logged_id = (int) s.getAttribute("userid");
                Utente u = datalayer.getUtente(logged_id);
                Task t = datalayer.getTask(ext_task);
                if(!t.getChiuso())
                    response.sendRedirect("Index");
                Progetto proj = t.getProgetto();
                List<Progetto> user_projects = u.getProgetti();
                boolean found = false;
                for(Progetto p : user_projects){
                    if(p.getKey() == proj.getKey())
                        found = true;
                }
                if(!found)
                    response.sendRedirect("Index");
                else{
                    if(edit){
                        datalayer.salvaCoprenti(0, ext_utente, ext_task);
                        response.sendRedirect("/SocialDevelop/Rate?proj_id="+proj.getKey());
                    } else {
                        
                        datalayer.salvaCoprenti(voto, ext_utente, ext_task);
                        System.out.println(voto);
                        if(async){
                            response.setContentType("text/plain");
                            response.setCharacterEncoding("UTF-8");
                            try (PrintWriter out = response.getWriter()) {
                                out.print(voto);
                            }
                        }else{
                            response.sendRedirect("/SocialDevelop/Rate?proj_id="+proj.getKey());
                        }
                    }
                }
            }
        }catch(DataLayerException ex){
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {

            setvote(request,response);
        } catch (DataLayerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
