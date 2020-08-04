<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Books Store Application</title>
</head>
<body>
<h1>
    Table Creation
</h1>
<div align="center">
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
</div>
</body>
</html>