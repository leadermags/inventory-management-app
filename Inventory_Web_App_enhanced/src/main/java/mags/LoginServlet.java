/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leadermags
 */
// controls the URL where the servlet is located
@WebServlet("/servlets/login")
public class LoginServlet extends HttpServlet {
    
    //private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use LogManager to log message for failed login attempt
        LogManager logManager = LogManager.getLogManager();
        String loggerName = LoginServlet.class.getName();
        Logger logger = Logger.getLogger(loggerName);
        logManager.addLogger(logger);
        
        try {
            // Create object class
            UserManager um = (UserManager) request.getServletContext().getAttribute("userManager");
            
            // Retrieve parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // Check if username and password are valid
            if(um.checkLogin(username, password)){
                // Retrieve User object and store in session
                HttpSession session = request.getSession();
                session.setAttribute("user", um.getUser(username));
                
                response.sendRedirect("../index.jsp");
            }else{
                String message = "Failed login attempt.";
                logger.setLevel(Level.WARNING);
                logger.log(Level.SEVERE, message);
                logger.log(Level.WARNING, message);
                
                request.getSession().setAttribute("errorMessage", message);
//                RequestDispatcher rd = request.getRequestDispatcher("../pages/login.jsp");
//                rd.include(request, response);
                //request.setAttribute("errorMessage", message);
                //request.getRequestDispatcher("../pages/login.jsp").forward(request, response);
                response.sendRedirect("../pages/login.jsp");
            }
            
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            logger.log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect("../pages/login.jsp");
        } catch (DataAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect("../pages/login.jsp");
        }
        
        
    }

}
