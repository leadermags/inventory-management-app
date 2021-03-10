<%-- 
 /*
    Maggie Lin
    Inventory Web App
    This program creates a web app to allow users to view the list in inventory
    and create, update, and delete a product to the inventory list.
 */
--%>
<%-- core tag provides variable support, URL management, flow control etc. --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- fmt tag to allow number formatting --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="leadermags.InventoryServlet"%>
<%@page import="leadermags.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%-- load InventoryManager.java --%>
        <jsp:useBean id="inventoryManager" class="leadermags.InventoryManager"/>
        <%-- set product with selected UPC --%>
        <c:set var="product" value="${inventoryManager.getProduct(param.upc)}" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            *{
                padding: 0 10px;
                text-align: center;
            }
            .inputs{
                display: inline-block;
                width: 380px;
            }
            label{
                display: inline-block;
                text-align: left;
                
            }
            #upc, #description, #price, #stock{
                display: inline-block;
                clear: both;
                float: right;
                text-align: left;
            }
            input[type=submit]{
                margin-top: 10px;
            }
        </style>
        <title>Edit Product Form</title>
    </head>
    <body>
        
        <div>
            <%-- use the request parameter to display current product's UPC --%>
            <h2>Editing Product #<%=request.getParameter("upc")%> </h2>
            
        <%-- 
            Form to edit product, will be redirected to InventoryServlet.java
            and upon successful submission, create an alert message.
            Form is also pre-filled from current product's data.
            Can only edit description, price, and stock; UPC is readonly.
        --%>
        <form action="../servlets/inventory" method="POST" >
            <div class="inputs">
                <label for="upc">Enter the UPC: </label>
                <input type="text" name="upc" id="upc" 
                       value="${product.upc}" readonly="readonly"/>
            </div>
            <div class="inputs">
                <label for="description">Enter the description: </label>
                <input type="text" name="description" id="description" 
                       value="${product.description}" required />
            </div>
            <div class="inputs">
                <label for="price">Enter the price: </label>
                <%-- format price in this format: $0.00 --%>
                <input type="number" step="0.01" name="price" id="price" 
                        value="<fmt:formatNumber type = "number" 
                        minFractionDigits = "2" value = "${product.price}" />" 
                        required />
            </div>
            <div class="inputs">
                <label for="stock">Enter the amount in stock: </label>
                <input type="number" name="stock" id="stock" 
                       value="${product.stock}" required />
            </div>
            <div>
                <%-- Buttons to Go Back or submit --%>
                <button type="button"  onclick="location.href='inventory.jsp'">
                    Go Back </button>
                &nbsp;&nbsp;&nbsp;
                <input type="submit" name="button" value="Edit" />
            </div>
        </form>
            
        </div>
    </body>
</html>
