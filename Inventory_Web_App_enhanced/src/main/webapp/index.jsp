<%-- 
/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="mags.UserManager"%>
<!DOCTYPE html>
<html>
    <head>
        <%-- load UserManager.java --%>
        <jsp:useBean id="userManager" scope="application" class="mags.UserManager"/>
        <title>Welcome</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <h1>Current User Logged in: ${sessionScope.user.username}</h1>
            <div>
                Module 4 Project:
                <ul>
                    <c:if test="${sessionScope.user.hasRole('INVENTORY_MANAGER') or
                            sessionScope.user.hasRole('INVENTORY_VIEWER')}">
                          <li><a href="pages/inventory.jsp">Inventory List</a></li>
                    </c:if>
                    <c:if test="${sessionScope.user.hasRole('USER_MANAGER')}">
                          <li><a href="pages/users.jsp">User List</a></li>
                    </c:if>
                    
                </ul>
                <form action="servlets/logout" method="POST" >
                    <%-- Button to logout --%>
                    <input type="submit" name="button" value="Log Out" />
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <h1>
                You are not logged in.
            </h1>
            <div>
                <button type="button"  onclick="location.href='pages/login.jsp'">
                Log In </button>
            </div>
        </c:otherwise>
    </c:choose>
        
    </body>
</html>