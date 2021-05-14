/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
package mags;

import java.io.IOException;
import java.math.BigDecimal;
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
 * This is the InventoryServlet. It creates a webpage for users to view
 * Inventory List then user can manipulate data in list.
 *
 * @author leadermags
 */
// controls the URL where the servlet is located
@WebServlet("/servlets/inventory")
public class InventoryServlet extends HttpServlet {

    /**
     *
     * Handles requests from the forms submitted from files: inventory.jsp,
     * deleteProduct.jsp, editProduct.jsp
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
        InventoryManager im = (InventoryManager) request.getServletContext().getAttribute("inventoryManager");
        Product product = new Product();

        // Handles what form was submitted
        String button = request.getParameter("button");
        // Receive parameters
        String upc;
        String description;
        String price;
        String stock;
        // Match parameters with HTML input name to create a product
        upc = request.getParameter("upc");
        description = request.getParameter("description");
        price = request.getParameter("price");
        stock = request.getParameter("stock");

        // Try/catch to convert price and stock to BigDecimal and Integer
        // set errorMessage on session scope and log error
        try {
            Integer stockInt = Integer.parseInt(stock);
            product.setStock(stockInt);
            BigDecimal priceBD = new BigDecimal(price);
            product.setPrice(priceBD);
        } catch(NumberFormatException ex){
            logger.log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect("../pages/inventory.jsp");
        }

        String message;

        try {
            switch (button) {
                case "Create":

                    // checks each product in list to prevent duplicate UPC
                    if (im.getProduct(upc) == null) {
                        // Add product to inventory list
                        im.addProduct(upc, description, price, stock);
                        message = "Product #" + upc + " created successfully.";

                        request.setAttribute("confirmMessage", message);
                        request.getRequestDispatcher("../pages/inventory.jsp").forward(request, response);

                    } else {
                        message = "UPC already exists. No changes made.";

                        request.setAttribute("errorMessage", message);
                        request.getRequestDispatcher("../pages/inventory.jsp").forward(request, response);

                    }

                    break;

                case "Edit":

                    // Edit product to inventory list
                    im.editProduct(upc, description, price, stock);
                    message = "Product #" + upc + " updated successfully.";

                    request.setAttribute("confirmMessage", message);
                    request.getRequestDispatcher("../pages/inventory.jsp").forward(request, response);

                    break;

                case "Delete":

                    // Delete product with matching UPC
                    im.deleteProduct(upc);
                    message = "Product #" + upc + " deleted successfully.";

                    request.setAttribute("confirmMessage", message);
                    request.getRequestDispatcher("../pages/inventory.jsp").forward(request, response);

                    break;

                default:
                    break;

            }

            // Send the response to update to webpage of Inventory List with message
            response.sendRedirect("../pages/inventory.jsp");

        } catch (DataAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect("../pages/inventory.jsp");
        }

    }

}
