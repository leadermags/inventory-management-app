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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%-- load userManager.java --%>
        <jsp:useBean id="userManager" class="mags.UserManager"/>
        <%-- set user with selected username --%>
        <c:set var="user" value="${userManager.getUser(param.username)}"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete User</title>
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
        
    </head>
    <body>
        <div>Are you sure you want to delete the following user?</div><br>
        <div>
            <%-- Display current user's data --%>
            <table>
                <tr>
                    <th class="label">User:</th>
                    <th class="detail">${user.getUsername()}</th>
                </tr>
                <tr>
                    <td class="label"><i>Roles:</i></td>
                    <td class="detail">${user.roles}</td>
                </tr>
                
            </table>
        </div>
        <br>
        <%-- 
            Form to delete user, will be redirected to UserServlet.java
            and upon successful submission, create an alert message.
        --%>
        <form action="../servlets/users" method="POST" >
            
            <%-- Buttons to Go Back or submit --%>
            <button type="button"  onclick="location.href='users.jsp'">
                Go Back </button>
            &nbsp;&nbsp;&nbsp;
            <input type="hidden" name="username" value="${user.username}" />
            <input type="submit" name="button" value="Delete" />
        </form>
    </body>
</html>
