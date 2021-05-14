<%-- 
/*
    Inventory and User Mangement Web App
    This program creates a web app to allow users to manipulate data in inventory
    and user list.
    May 3, 2021
 */
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%-- load UserManager.java --%>
        <jsp:useBean id="userManager" scope="application" class="mags.UserManager"/>
        <c:set var="hasUserMan" scope="application" value="${userManager.hasUserMan()}" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Please Login</h1>
        <form action="../servlets/login" method="POST" >
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
                    
                    <c:if test="${not empty sessionScope.errorMessage}" >
                        <span style="color: red;">${errorMessage}</span><br>
                        <c:remove var="errorMessage" />
                    </c:if>
                    
                    <div>
                        <%-- Submit form to login --%>
                        &nbsp;&nbsp;&nbsp;
                        <input type="submit" name="button" value="Sign in" />

                    </div>

                    <br><br>

                    </form>
                    </body>
                    </html>
