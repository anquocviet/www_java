<%@ page import="java.util.Set" %>
<%@ page import="fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto" %>
<%@ page import="fit.se.week03_lab_anquocviet_21080821.dtos.EmployeeDto" %>
<%@ page import="fit.se.week03_lab_anquocviet_21080821.dtos.CustomerDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %><%--
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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

<%
    Set<ProductDto> products = (Set<ProductDto>) request.getAttribute("products");
%>

<body>
<form action="controller" method="post">

    <input type="hidden" name="action" value="buy">
    <h1>Employees</h1>
    <select name="employeeId" class="select-employee">

    </select>
    <h1>Customers</h1>
    <select name="customerId" class="select-customer">
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
            <td><%= product.price().price() %>
            </td>
            <td>
                <input type="hidden" name="productId" value="<%= product.id() %>">
                <input type="number" name="quantity" value="1">
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <button class="pay" type="submit" name="action" value="buy">Buy</button>
</form>

<script>
    $(document).ready(function () {
        $.getJSON('api/employees', function (data) {
            data.forEach(function (employee) {
                $('.select-employee').append(`<option value="\${employee.id}">
                    \${employee.fullName} - \${employee.phone}
                </option>`);
            });
        });
    });
    $(document).ready(function () {
        $.getJSON('api/customers', function (data) {
            data.forEach(function (customer) {
                $('.select-customer').append(`<option value="\${customer.id}">
                \${customer.name} - \${customer.phone}
                </option>`);
            });
        });
    });

</script>
</body>
</html>
