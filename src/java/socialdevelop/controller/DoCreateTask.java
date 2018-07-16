/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Tipo;
import socialdevelop.data.model.Utente;

/**
 *
 * @author Davide De Marco
 */
public class DoCreateTask extends SocialDevelopBaseController{
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_create_task(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        
        String nome = request.getParameter("nome");
        String maxc = request.getParameter("maxcollab");
        String chiuso = request.getParameter("chiuso");
        String deadline = request.getParameter("deadline");
        String descrizione = request.getParameter("descrizione");
        int tipoid = SecurityLayer.checkNumeric(request.getParameter("tipo"));
        
        GregorianCalendar dl = new GregorianCalendar();
        dl.setLenient(false);
        dl.set(GregorianCalendar.YEAR, Integer.valueOf(deadline.split("-")[0]));
        dl.set(GregorianCalendar.MONTH, Integer.valueOf(deadline.split("-")[1]) - 1); // Bello Java, eh?
        dl.set(GregorianCalendar.DATE, Integer.valueOf(deadline.split("-")[2]));
        
        int maxcoll = Integer.parseInt(maxc);
        int progettoid = SecurityLayer.checkNumeric(request.getParameter("progettoiddi"));
        Progetto progetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(progettoid);
        Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(tipoid);
        GregorianCalendar time = new GregorianCalendar();
            
        boolean close;
        if (chiuso.equals("0")) {
            close = false;
        } else {
            close = true;
        }
        
        Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaTask();
        task.setNome(nome);
        task.setNumeroMassimoCollaboratori(maxcoll);
        task.setChiuso(close);
        task.setDataInizio(time);
        task.setDataFine(dl);
        task.setNumeroCorrenteCollaboratori(0);
        task.setProgetto(progetto);
        task.setDescrizione(descrizione);
        task.setTipo(tipo);
        
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaTask(task);
        
        response.sendRedirect("TaskPage?task_id="+task.getKey());
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_create_task(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
