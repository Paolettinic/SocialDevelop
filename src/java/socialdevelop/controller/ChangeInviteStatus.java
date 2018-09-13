package socialdevelop.controller;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import socialdevelop.data.model.Invito;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.mailer.MailSender;

/**
 * @author Mario Vetrini
 */

public class ChangeInviteStatus extends SocialDevelopBaseController {
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }
    
    private void action_change_invite_status(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException, SQLException, NamingException {
        HttpSession s = request.getSession(true);
        if (s.getAttribute("userid") == null || (int) s.getAttribute("userid") == 0) {
            response.sendRedirect("Login");
            return;
        }
        
        Invito invito = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getInvito(Integer.valueOf(request.getParameter("invito_key")));
        
        Task task_invito = invito.getTask();
        //MailSender ms = new MailSender();
        if (null != request.getParameter("stato"))
            switch (request.getParameter("stato")) {
                case "Annullato":
                    invito.setStato("Annullato");
                    ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaInvito(invito);
                    response.sendRedirect("OffersApplicationsPanel");
                    return;
                case "Rifiutato":
                    invito.setStato("Rifiutato");
                    ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaInvito(invito);
                    response.sendRedirect("OffersApplicationsPanel");
                    return;
                case "Accettato":
                    task_invito.setNumeroCorrenteCollaboratori(task_invito.getNumeroCorrenteCollaboratori() + 1);
                    if (task_invito.getNumeroCorrenteCollaboratori() == task_invito.getNumeroMassimoCollaboratori()) {
                        task_invito.setChiuso(true);
                    }
                    invito.setStato("Accettato");
                    ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaInvito(invito);
                    ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaCoprenti(0, invito.getUtente().getKey(), task_invito.getKey());
                    response.sendRedirect("OffersApplicationsPanel");
                    return;
                default:
                    break;
            }

        response.sendRedirect("OffersApplicationsPanel");
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_change_invite_status(request, response);
        } catch (IOException | TemplateManagerException | DataLayerException | SQLException | NamingException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
