<%-- 
/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
--%>
<%-- core tag provides variable support, URL management, flow control etc. --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- fmt tag to allow number formatting --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="mags.InventoryServlet"%>
<%@page import="mags.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <%-- load InventoryManager.java --%>
        <jsp:useBean id="inventoryManager" scope="application" class="mags.InventoryManager"/>
        <title>Inventory</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            table{
                margin-left: auto;
                margin-right: auto;
            }
            td, th {
                border: 1px solid #ddd;
                padding: 8px;
            }
            tr:nth-child(even){
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #ddd;}
            th {
                padding-top: 12px;
                padding-bottom: 12px;
                background-color: #4682B4;
                color: white;
            }
        </style>
    </head>
    <body>
        <h1>Inventory List</h1>

        <div>
            <table>
                <tr>
                    <th>UPC</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th><i>Update</i></th>
                </tr>
                <%-- goes through each product in list and outputs for each row --%>

                <c:forEach var="p" items="${inventoryManager.getProductList()}" >
                    <div>
                        <tr>
                            <td>${p.getUpc()}</td>
                            <td>${p.getDescription()}</td>
                            <%-- format price in this format: $0.00 --%>
                            <td><fmt:formatNumber value = "${p.price}" 
                                              type = "currency"/></td>
                            <td>${p.getStock()}</td>
                            <%-- Links to redirect to Edit or Delete product --%>
                            <td><a href="/Module_4_Project/pages/editProduct.jsp?upc=${p.upc}">Edit</a>&nbsp;&nbsp;
                                <a href="/Module_4_Project/pages/deleteProduct.jsp?upc=${p.upc}">Delete</a></td>
                        </tr>
                    </div>
                </c:forEach>

            </table>
        </div>
        <%-- Display message from InventoryServlet --%>
        <c:if test="${not empty requestScope.confirmMessage}" >

            <span style="color: green;">${confirmMessage}</span><br>
            <c:remove var="confirmMessage" />
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}" >

            <span style="color: red;">${errorMessage}</span><br>
            <c:remove var="errorMessage" />
        </c:if>


        <div>
            <h2>Add a new Product</h2>
            <%-- 
                Form to create new product, will be redirected to InventoryServlet.java
                and upon successful submission, create an alert message.
            --%>
            <form action="../servlets/inventory" method="POST" >
                <div class="inputs">
                    <label for="upc">Enter the UPC: </label>
                    <input type="text" name="upc" id="upc" required />
                </div>
                <div class="inputs">
                    <label for="description">Enter the description: </label>
                    <input type="text" name="description" id="description" required />
                </div>
                <div class="inputs">
                    <label for="price">Enter the price: </label>
                    <input type="number" step="0.01" name="price" id="price" required />
                </div>
                <div class="inputs">
                    <label for="stock">Enter the amount in stock: </label>
                    <input type="number" name="stock" id="stock" required />
                </div>
                <div>
                    <input type="submit" name="button" value="Create" />
                </div>
            </form>
        </div>
        <br>

        <div id="link">
            <a href="../index.jsp">Home</a>
        </div>
    </body>
</html>
