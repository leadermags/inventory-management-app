/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * This class implements the Filter interface to restrict access to specific
 * pages.
 *
 * @author leadermags
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {

    /**
     *
     */
    @Override
    public void destroy() {

    }

    /**
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     *
     * Drives the filters for pages
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // convert to HttpServletRequest and HttpServletResponse objects
        // because this app only handles HTTP requests
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // context path
        String page = httpRequest.getServletPath();

        // current User session bean
        HttpSession session = httpRequest.getSession(true);
        User u = (User) session.getAttribute("user");
        
        // allow access regardless of whether or not user session bean is set
        if (page.equals("/pages/login.jsp") || page.equals("/servlets/login")) {
            chain.doFilter(request, response);
            
        } else if (u != null) {
            // default allow access if user session bean is set
            if (page.equals("/index.jsp") || page.equals("/servlets/logout")) {
                chain.doFilter(request, response);
                
                // allow access to any page if user has administrator role
            } else if (u.hasRole("ADMINISTRATOR")) {
                chain.doFilter(request, response);
                
                // allow access if user has user manager (or administrator) role
            } else if (page.equals("/pages/users.jsp")
                    || page.equals("/pages/editUser.jsp")
                    || page.equals("/pages/deleteUser.jsp")
                    || page.equals("/servlets/users")) {
                if (u.hasRole("USER_MANAGER")) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.sendRedirect("pages/login.jsp");
                }
                
                // allow access if user has inventory manager or viewer (or administrator) role
            } else if (page.equals("/pages/inventory.jsp")) {
                if (u.hasRole("INVENTORY_MANAGER") || u.hasRole("INVENTORY_VIEWER")) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.sendRedirect("pages/login.jsp");
                }
                
                // allow access if user has inventory manager (or administrator) role
            } else if (page.equals("/servlets/inventory")
                    || page.equals("/pages/editProduct.jsp")
                    || page.equals("/pages/deleteProduct.jsp")) {
                if (u.hasRole("INVENTORY_MANAGER")) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.sendRedirect("../pages/login.jsp");
                }
                
                // if user does not have access redirect to login
            } else {
                httpResponse.sendRedirect("/Module_4_Project/pages/login.jsp");
            }
        } else {
            httpResponse.sendRedirect("/Module_4_Project/pages/login.jsp");
        }
    }

}
