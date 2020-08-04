<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
        <title>Supermarket Application</title>
</head>
<body>
    <div align="center">
        <h1>Shopper Management and Information</h1>
        <h2><a href="http://localhost:8080/Bookstore_war_exploded/name">Table Statistics</a></h2>
    <table border="1" cellpadding="5">
        <caption><h2>List of Shoppers</h2></caption>
        <tr>
            <th>ID</th>
            <th>Time Entered</th>
            <th>Time Spent</th>
            <th>Rush Status</th>
            <th>Senior</th>
        </tr>
        <c:forEach var="shopper" items="${listShopper}">
        <tr>
            <td><c:out value="${shopper.id}" /></td>
            <td><c:out value="${shopper.time_entered}" /></td>
            <td><c:out value="${shopper.time_spent}" /></td>
            <td><c:out value="${shopper.rush}" /></td>
            <td><c:out value="${shopper.is_senior}" /></td>
        </tr>
        </c:forEach>
    </table>
</div>   
</body>
</html>