/*
    Maggie Lin
    Inventory Web App
    This program creates a web app to allow users to view the list in inventory
    and create, update, and delete a product to the inventory list.
 */
package leadermags;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * This is the main app and creates a webpage for users to view Inventory List
 * and a link to a form to add a product.
 * @author Maggie Lin 
 */
// controls the URL where the servlet is located
@WebServlet("/servlets/inventory")          
public class InventoryServlet extends HttpServlet {
    
    private static final String INVENTORY = System.getProperty("user.home")
            + "/inventory.dat";

    /**
     * 
     * Handles requests from the forms submitted from files: 
     * inventory.jsp, deleteProduct.jsp, editProduct.jsp
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Create object class
        InventoryManager im = new InventoryManager();
        
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
        
        // initialize message
        String message = "No changes made.";
        
        try{
            switch (button) {
                case "Create":

                    // checks each product in list to prevent duplicate UPC
                    if (im.getProduct(upc) == null) {
                        // Add product to inventory list
                        im.addProduct(upc, description, price, stock);
                        message = "Product created successfully.";
                    }
                    else{
                        message = "UPC already exists. No changes made.";
                    }
                    
                    break;

                case "Edit":
                    
                    // Edit product to inventory list
                    im.editProduct(upc, description, price, stock);
                    message = "Product updated successfully.";
                    break;
                    
                case "Delete":
                    
                    // Delete product with matching UPC
                    im.deleteProduct(upc);
                    message = "Product deleted successfully.";
                    break;
                    
                default:
                    break;
            }
        }catch (NullPointerException e){
            
        }
        
        // Send the response to update to webpage of Inventory List with message
        response.sendRedirect("../pages/inventory.jsp?message=" + URLEncoder.encode(message));
    }

}
