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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * This is the UserServlet. It creates a webpage for users to view User List
 * then user can manipulate data in list.
 *
 * @author leadermags
 */
// controls the URL where the servlet is located
@WebServlet("/servlets/users")
public class UserServlet extends HttpServlet {

    /**
     *
     * Handles requests from the forms submitted from files: users.jsp,
     * deleteUser.jsp, editUser.jsp
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use LogManager to load Logger class
        LogManager logManager = LogManager.getLogManager();
        String loggerName = InventoryServlet.class.getName();
        Logger logger = Logger.getLogger(loggerName);
        logManager.addLogger(logger);

        // Create object class
        UserManager um = (UserManager) request.getServletContext().getAttribute("userManager");
        User user = new User();

        // Handles what form was submitted
        String button = request.getParameter("button");

        // Receive parameters and match parameters with HTML input name to create a user
        String username;
        username = request.getParameter("username");
        String password;
        password = request.getParameter("password");
        String message;
        
        try {
            switch (button) {
                case "Create":

                    user.setUsername(username);
                    // checks each user in list to prevent duplicate username
                    if (um.getUser(username) == null) {
                        user.setPassword(password);
                        // get array of roles then turn into set
                        String[] selectRoles = request.getParameterValues("roles");
                        if (selectRoles != null && selectRoles.length != 0) {
                            Set<String> roles = new HashSet<>(Arrays.asList(selectRoles));
                            user.setRoles(roles);
                            // Add user to users list
                            um.addUser(user);
                            message = "User " + username + " created successfully.";
                    
                            request.setAttribute("confirmMessage", message);
                            request.getRequestDispatcher("../pages/users.jsp").forward(request, response);
                            
                        }
                    } else {
                        message = "User already exists. No changes made.";
                        // Error Message
                        request.setAttribute("errorMessage", message);
                        request.getRequestDispatcher("../pages/users.jsp").forward(request, response);

                    }

                    break;

                case "Edit":

                    user.setUsername(username);
                    // Change password
                    String newPassword = request.getParameter("newPassword");
                    if (!newPassword.equals("")) {
                        um.changePassword(username, newPassword);
                    }
                    // Edit user role to users list
                    String[] selectRoles = request.getParameterValues("roles");
                    if (selectRoles != null && selectRoles.length != 0) {
                        Set<String> roles = new HashSet<>(Arrays.asList(selectRoles));
                        user.setRoles(roles);
                        um.updateRoles(username, roles);
                    }
                    message = "User " + username + " updated successfully.";
                    
                    request.setAttribute("confirmMessage", message);
                    request.getRequestDispatcher("../pages/users.jsp").forward(request, response);
                    
                    break;

                case "Delete":

                    user.setUsername(username);
                    // Delete user with matching username
                    um.removeUser(username);
                    message = "User " + username + " deleted successfully.";
                    request.setAttribute("confirmMessage", message);
                    request.getRequestDispatcher("../pages/users.jsp").forward(request, response);
                    
                    break;

                default:
                    break;
            }
            response.sendRedirect("../pages/users.jsp");
        } catch (NullPointerException | DataAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect("../pages/users.jsp");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            logger.log(Level.SEVERE, null, ex);
            response.sendRedirect("../pages/users.jsp");
            request.getSession().setAttribute("errorMessage", ex.getMessage());
        }

    }

}
