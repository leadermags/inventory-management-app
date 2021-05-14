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
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <%-- load UserManager.java --%>
        <jsp:useBean id="userManager" scope="application" class="mags.UserManager"/>
        <title>Users</title>
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
            input[type=submit]{
                margin-top: 10px;
            }
            table{
                margin-left: auto;
                margin-right: auto;
                text-align: center;
            }
            td, th {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: center;
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
        <h1>User List</h1>
        <div>
            <table>
                <tr>
                    <th>Username</th>
                    <th>Roles</th>
                    <th><i>Update</i></th>
                </tr>
                <%-- goes through each user in list and outputs for each row --%>

                <c:forEach items="${userManager.getUsers()}" var="u">
                    <div>
                        <tr>
                            <td>${u.getUsername()}</td>
                            <td>
                                <%-- prints each role in roles set --%>
                                <c:forEach items="${u.getRoles()}" var="r">
                                    ${r}<br>
                                </c:forEach>
                            </td>
                            <%-- Links to redirect to Edit or Delete user --%>
                            <td><a href="/Module_4_Project/pages/editUser.jsp?username=${u.username}">Edit</a>&nbsp;&nbsp;
                                <a href="/Module_4_Project/pages/deleteUser.jsp?username=${u.username}">Delete</a></td>
                        </tr>
                    </div>
                </c:forEach>

            </table>
        </div>
        <%-- Display message from UserServlet --%>
        <c:if test="${not empty requestScope.confirmMessage}" >
            
            <span style="color: green;">${confirmMessage}</span><br>
            <c:remove var="confirmMessage" />
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}" >
            
            <span style="color: red;">${errorMessage}</span><br>
            <c:remove var="errorMessage" />
        </c:if>

        <div>
            <h2>Add a new User</h2>
            <%-- 
                Form to create new user, will be redirected to UserServlet.java
                and upon successful submission, create an alert message.
            --%>
            <form action="../servlets/users" method="POST" >
                <div class="inputs">
                    <label for="username">Enter the username: </label>
                    <input type="text" name="username" id="username" required />
                    <br>
                </div>
                <div class="inputs">
                    <label for="password">Enter the password: </label>
                    <input type="password" name="password" id="password" required />
                    <br>
                    </<div>
                        <div class="inputs">
                            Select the roles: <br>
                            <input type="checkbox" id="admin" name="roles" value="ADMINISTRATOR">
                            <label for="roles">Administrator</label><br>
                            <input type="checkbox" id="invman" name="roles" value="INVENTORY_MANAGER">
                            <label for="roles">Inventory Manager</label><br>
                            <input type="checkbox" id="usrman" name="roles" value="USER_MANAGER">
                            <label for="roles">User Manager</label><br>
                            <input type="checkbox" id="viewer" name="roles" value="INVENTORY_VIEWER">
                            <label for="roles">Viewer</label><br>
                        </div>
                        <div>
                            <%-- Buttons to Home or submit --%>
                            <button type="button"  onclick="location.href = '../index.jsp'">
                                Home </button>
                            &nbsp;&nbsp;&nbsp;
                            <input type="submit" name="button" value="Create" />
                        </div>
                        </form>
                    </div>
                    <br>

                    </body>
                    </html>
