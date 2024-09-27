<%@ page import="java.util.Set" %>
<%@ page import="fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto" %>
<%@ page import="fit.se.week03_lab_anquocviet_21080821.dtos.EmployeeDto" %>
<%@ page import="fit.se.week03_lab_anquocviet_21080821.dtos.CustomerDto" %><%--
  Created by IntelliJ IDEA.
  User: vie
  Date: 20/9/24
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
</head>

<style>
    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        border: 1px solid #ddd;
        padding: 8px;
    }

    th {
        background-color: #f2f2f2;
        text-align: left;
    }

    select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
</style>

<body>
<%
    Set<ProductDto> products = (Set<ProductDto>) request.getAttribute("products");
    Set<EmployeeDto> employees = (Set<EmployeeDto>) request.getAttribute("employees");
    Set<CustomerDto> customers = (Set<CustomerDto>) request.getAttribute("customers");
%>
<form action="controller" method="post">
    <h1>Employees</h1>
    <select>
        <% for (EmployeeDto employee : employees) { %>
        <option value="<%= employee.id() %>"><%= employee.fullName()  %> - (<%= employee.phone() %>)
        </option>
        <% } %>
    </select>
    <h1>Customers</h1>
    <select>
        <% for (CustomerDto customer : customers) { %>
        <option value="<%= customer.id() %>"><%= customer.name()  %> - (<%= customer.phone() %>)
        </option>
        <% } %>
    </select>

    <h1>Products</h1>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Unit</th>
            <th>Manufacturer</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        </thead>
        <tbody id="productTableBody">
        <% for (ProductDto product : products) { %>
        <tr>
            <td><%= product.name() %>
            </td>
            <td><%= product.description() %>
            </td>
            <td><%= product.unit() %>
            </td>
            <td><%= product.manufacturer() %>
            </td>
            <td><%= product.prices().iterator().next().price() %>
            </td>
            <td><input type="number" name="quantity" value="1" min="1"/></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <button type="submit" name="action" value="buy">Buy</button>
</form>
</body>
</html>
