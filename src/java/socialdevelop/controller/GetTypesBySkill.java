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
import java.util.Arrays;
import java.util.List;
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
public class GetTypesBySkill extends SocialDevelopBaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static void getTypes(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataLayerException, IOException{
        int skill_id = request.getParameter("skill_id") == null? -1 :SecurityLayer.checkNumeric(request.getParameter("skill_id"));
        Skill skill;
        List<Tipo> typelist;
        int[] type_id_list;
        try{
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            skill = datalayer.getSkill(skill_id);
            System.out.println(skill);
            typelist = datalayer.getTipi(skill);
            System.out.println(typelist);
            type_id_list = new int[typelist.size()];
            for(Tipo t : typelist){
                type_id_list[typelist.indexOf(t)] = t.getKey();
                System.out.println(type_id_list[typelist.indexOf(t)]);
            }
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print(Arrays.toString(type_id_list));
            }
        }catch(DataLayerException ex){
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            getTypes(request,response);
        } catch (DataLayerException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetVote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

}
