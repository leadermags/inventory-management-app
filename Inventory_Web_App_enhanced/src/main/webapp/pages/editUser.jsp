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
        <%-- load UserManager.java --%>
        <jsp:useBean id="userManager" class="mags.UserManager"/>
        <%-- set user with selected username --%>
        <c:set var="user" value="${userManager.getUser(param.username)}" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            *{
                padding: 0 10px;
                text-align: center;
            }
            .inputs{
                display: inline-block;
                width: 380px;
                text-align: center;
            }
            label{
                display: inline-block;
                text-align: center;
            }
            input[type=submit]{
                margin-top: 10px;
            }
        </style>
        <title>Edit User Form</title>
    </head>
    <body>
        
        <div>
            <%-- use the request parameter to display current user's username --%>
            <h2>Editing User #<%=request.getParameter("username")%> </h2>
            
        <%-- 
            Form to edit user, will be redirected to UserServlet.java
            and upon successful submission, create an alert message.
            Form is also pre-filled from current user's data.
            Can only edit user's roles; username is readonly.
        --%>
        <form action="../servlets/users" method="POST" >
            <div class="inputs">
                <label for="username">Enter the username: </label>
                <input type="text" name="username" id="username" 
                       value="${user.username}" readonly="readonly"/>
                <br>
            </div>
            <div class="inputs">
                <label for="newPassword">Change password: </label>
                <input type="password" name="newPassword" id="newPassword" />
                <br>
            </div>
            <div class="inputs">
                Select the roles: <br>
                <%-- 
                    multiple if, else type statements to test if user has roles
                    then decide whether checkboxes are checked or not
                --%>
                <c:choose>
                    <c:when test="${user.hasRole('ADMINISTRATOR')}">
                        <input type="checkbox" id="admin" name="roles" value="ADMINISTRATOR" checked="true" />
                        <label for="roles">Administrator</label><br>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" id="admin" name="roles" value="ADMINISTRATOR">
                        <label for="roles">Administrator</label><br>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${user.hasRole('INVENTORY_MANAGER')}">
                        <input type="checkbox" id="invman" name="roles" value="INVENTORY_MANAGER" checked="true">
                        <label for="roles">Inventory Manager</label><br>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" id="invman" name="roles" value="INVENTORY_MANAGER">
                        <label for="roles">Inventory Manager</label><br>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${user.hasRole('USER_MANAGER')}">
                        <input type="checkbox" id="usrman" name="roles" value="USER_MANAGER" checked="true">
                        <label for="roles">User Manager</label><br>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" id="usrman" name="roles" value="USER_MANAGER">
                        <label for="roles">User Manager</label><br>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${user.hasRole('INVENTORY_VIEWER')}">
                        <input type="checkbox" id="viewer" name="roles" value="INVENTORY_VIEWER" checked="true">
                        <label for="roles">Viewer</label><br>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" id="viewer" name="roles" value="INVENTORY_VIEWER">
                        <label for="roles">Viewer</label><br>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div>
                <%-- Buttons to Go Back or submit --%>
                <button type="button"  onclick="location.href='users.jsp'">
                    Go Back </button>
                &nbsp;&nbsp;&nbsp;
                <input type="submit" name="button" value="Edit" />
            </div>
        </form>
            
        </div>
    </body>
</html>
