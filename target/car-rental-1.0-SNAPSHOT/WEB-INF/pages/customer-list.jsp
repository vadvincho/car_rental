<%--
  Created by IntelliJ IDEA.
  User: Vadzim Vincho
  Date: 18.05.2021
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer List</title>
    <style>
        table {
            margin-top: 10px;
            border: solid black 1px;
        }

        table td {
            padding: 5px;
        }

        .message {
            font-size: 90%;
            color: blue;
            font-style: italic;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/customer-create">Create Customer</a>
<br/>

<table border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>passport</th>
        <th>phoneNumber</th>
        <th>money</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${customers}" var="customer">
        <tr>
            <td> ${customer.id} </td>
            <td> ${customer.name} </td>
            <td> ${customer.passport} </td>
            <td> ${customer.phoneNumber} </td>
            <td> ${customer.money} </td>
            <td>
                <form action="/customer/delete" method="get">
                    <input type="text" hidden="true" name="id" value="${customer.id}">
                    <input class="btn btn-primary" type="submit" value="Delete">
                </form>
            </td>
            <td>
                <form action="/customer/edit" method="get">
                    <input type="text" hidden="true" name="id" value="${customer.id}">
                    <input class="btn btn-primary" type="submit" value="Edit">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
