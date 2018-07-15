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
        if (invito.getUtente().getKey() == (int) s.getAttribute("userid")) {
            invito.setStato(request.getParameter("stato"));
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).salvaInvito(invito);
        } else {
            response.sendRedirect("Index");
            return;
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
