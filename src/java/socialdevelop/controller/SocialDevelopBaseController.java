/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.result.FailureResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import socialdevelop.data.impl.SocialDevelopDataLayerMysqlImpl;
import socialdevelop.data.model.SocialDevelopDataLayer;


/**
 *
 * @author Giuseppe Della Penna
 */
public abstract class SocialDevelopBaseController extends HttpServlet {

    @Resource(name = "jdbc/SocialDevelop")
    private DataSource ds;

    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException;

    private void processBaseRequest(HttpServletRequest request, HttpServletResponse response) {
        //WARNING: never declare DB-related objects including references to Connection and Statement (as our data layer)
        //as class variables of a servlet. Since servlet instances are reused, concurrent requests may conflict on such
        //variables leading to unexpected results. To always have different connections and statements on a per-request
        //(i.e., per-thread) basis, declare them in the doGet, doPost etc. (or in methods called by them) and 
        //(possibly) pass such variables through the request.
        try (SocialDevelopDataLayer datalayer = new SocialDevelopDataLayerMysqlImpl(ds)) {
            datalayer.init();
            request.setAttribute("datalayer", datalayer);
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace(); //for debugging only
            (new FailureResult(getServletContext())).activate(
                    (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

}
