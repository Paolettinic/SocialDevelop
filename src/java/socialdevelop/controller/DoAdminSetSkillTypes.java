/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
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

    private void addSkill(HttpServletRequest request, HttpServletResponse response,int skillId, List<Integer> tipi_selezionati) throws ServletException, DataLayerException, IOException{
        try{
            SocialDevelopDataLayer datalayer = (SocialDevelopDataLayer) request.getAttribute("datalayer");
            Skill skill = datalayer.getSkill(skillId);
            List<Tipo> tipi_skill = skill.getTipi();
            List<Integer> selected_types = new ArrayList<>();
            selected_types.addAll(tipi_selezionati);
            System.out.println(tipi_selezionati);
            for(Tipo t : tipi_skill){
                if(selected_types.contains(t.getKey())){
                    System.out.println("La skill "+skillId+" è già associata al tipo "+t.getKey());
                    selected_types.remove((Integer)t.getKey());
                }else{
                    datalayer.eliminaAppartenenti(skillId, t.getKey());
                }
            }
            System.out.println("Tipi rimasti: "+selected_types);
            for(int i : selected_types){
                System.out.println("salvataggio tipo "+i+" per skill "+skillId);
                datalayer.salvaAppartenenti(skillId, i);
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
            List<Integer> tipi_selezionati = new ArrayList<>();
            for(int i=0;i<types_string.length;i++){
                tipi_selezionati.add(SecurityLayer.checkNumeric(types_string[i]));
            }
            addSkill(request,response,skill,tipi_selezionati);
        } catch (DataLayerException ex) {
            Logger.getLogger(DoAdminSetSkillTypes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DoAdminSetSkillTypes.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

}
