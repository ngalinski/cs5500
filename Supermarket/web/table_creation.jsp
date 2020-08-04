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
    <form action="insert" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>Month: </th>
                <td>
                    <input type="text" name="month" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Day: </th>
                <td>
                    <input type="text" name="day" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Year: </th>
                <td>
                    <input type="text" name="year" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Good Weather (y/n): </th>
                <td>
                    <input type="text" name="weather" size="45"/>
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