<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Books Store Application</title>
</head>
<body>
<div align="center">
    <h1>
        Table Query
    </h1>

    <form action="setName" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>Table Name: </th>
                <td>
                    <input type="text" name="tableName" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Earliest Enter Time: </th>
                <td>
                    <input type="text" name="before" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Latest Enter Time: </th>
                <td>
                    <input type="text" name="after" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Lowest Time Spent: </th>
                <td>
                    <input type="text" name="min" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Highest Time Spent: </th>
                <td>
                    <input type="text" name="max" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Rush Status: </th>
                <td>
                    <input type="text" name="rush" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Senior: </th>
                <td>
                    <input type="text" name="senior" size="45"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
    </form>


    <table border="1" cellpadding="5">
        <caption><h2>List of Tables</h2></caption>
        <tr>
            <th>Table Names</th>
        </tr>
        <c:forEach var="table" items="${listTables}">
        <tr>
            <td><c:out value="${table.name}" /></td>
        </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>