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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%-- load InventoryManager.java --%>
        <jsp:useBean id="inventoryManager" class="leadermags.InventoryManager"/>
        <%-- set product with selected UPC --%>
        <c:set var="product" value="${inventoryManager.getProduct(param.upc)}"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            *{
                padding: 0 10px;
                text-align: center;
            }
            table{
                margin-left: auto;
                margin-right: auto;
            }
            .label{
                text-align: right;
            }
            .detail{
                text-align: left;
            }
        </style>
        <title>Delete Product</title>
    </head>
    <body>
        <div>Are you sure you want to delete the following product?</div><br>
        <div>
            <%-- Display current product's data --%>
            <table>
                <tr>
                    <th class="label">Product&nbsp;&nbsp;</th>
                    <th class="detail">#${product.upc}</th>
                </tr>
                <tr>
                    <td class="label"><i>Description:</i></td>
                    <td class="detail">${product.description}</td>
                </tr>
                <tr>
                    <td class="label"><i>Price:</i></td>
                    <%-- format price in this format: $0.00 --%>
                    <td class="detail"><fmt:formatNumber value = "${product.price}" 
                              type = "currency"/></td>
                </tr>
                <tr>
                    <td class="label"><i>Stock:</i></td>
                    <td class="detail">${product.stock}</td>
                </tr>
            </table>
        </div>
        <br>
        <%-- 
            Form to delete product, will be redirected to InventoryServlet.java
            and upon successful submission, create an alert message.
        --%>
        <form action="../servlets/inventory" method="POST" >
            <%-- Buttons to Go Back or submit --%>
            <button type="button"  onclick="location.href='inventory.jsp'">
                Go Back </button>
            &nbsp;&nbsp;&nbsp;
            <input type="hidden" name="upc" value="${product.upc}" />
            <input type="submit" name="button" value="Delete" />
        </form>
    </body>
</html>
