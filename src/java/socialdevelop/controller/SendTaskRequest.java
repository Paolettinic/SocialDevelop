
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
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Invito;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Task;
import socialdevelop.mailer.MailSender;

/**
 *
 * @author Davide De Marco
 */

public class SendTaskRequest extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_do_send_request(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        
        HttpSession s = request.getSession(true);
        String mailTo;
        int taskid = SecurityLayer.checkNumeric(request.getParameter("taskiddi"));
        int utente_key = (int) s.getAttribute("userid");
        GregorianCalendar date = new GregorianCalendar();
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(utente_key);
        
        Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(taskid);
        Progetto progetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(task.getProgetto().getKey());
        Utente utente2 = null;
        
        if(utente.getKey()==progetto.getUtente().getKey()){
            int devkey = SecurityLayer.checkNumeric(request.getParameter("utente_key"));
            utente2 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(devkey);
        }
        
        Invito invito = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).creaInvito();
        if(utente.getKey()!=progetto.getUtente().getKey()){
            invito.setMessaggio("L'utente "+utente.getNome()+" "+utente.getCognome()+" vorrebbe partecipare al task");
            invito.setOfferta(true);
            invito.setUtente(utente);
            mailTo = utente.getEmail();
        }
        else{
            invito.setMessaggio("Il coordinatore "+utente.getNome()+" "+utente.getCognome()+"vorrebbe tu partecipassi al task");
            invito.setOfferta(false);
            invito.setUtente(utente2);
            mailTo = utente2.getEmail();
        }
        
        invito.setDataInvio(date);
        invito.setTask(task);
        
        invito.setStato("In attesa");
        
        
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaInvito(invito);
        
        MailSender ms = new MailSender(mailTo,"Task: "+task,invito.getMessaggio());
        
        //ms.sendMail();
        
        response.sendRedirect("TaskPage?task_id="+task.getKey());
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_do_send_request(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
