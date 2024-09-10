<%@ page import="fit.se.week01_lab_anquocviet_21080821.services.AccountService" %>
<%@ page import="fit.se.week01_lab_anquocviet_21080821.entities.Account" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %><%--
  Created by IntelliJ IDEA.
  User: vie
  Date: 9/9/24
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>List Account of Role</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 600px;
            text-align: center;
        }

        h3 {
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #f4f4f4;
        }

        .button_1 {
            height: 38px;
            background: #77aaff;
            border: 0;
            padding-left: 20px;
            padding-right: 20px;
            color: #fff;
            cursor: pointer;
            border-radius: 4px;
        }

        .button_1:hover {
            background-color: #5599dd;
        }
    </style>
</head>
<body>
<%
    Set<Account> accounts = (Set<Account>) request.getAttribute("accounts");
    if (accounts == null) {
        accounts = new HashSet<>();
    }
%>
<div class="container">
    <h3>Accounts for Role: <%= request.getParameter("role") %>
    </h3>
    <% if (accounts != null && !accounts.isEmpty()) { %>
    <table>
        <tr>
            <th>Full Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Status</th>
        </tr>
        <% for (Account account : accounts) { %>
        <tr>
            <td><%= account.getFullName() %>
            </td>
            <td><%= account.getEmail() %>
            </td>
            <td><%= account.getPhone() %>
            </td>
            <td><%= account.getStatus() == 1 ? "Active" : "Inactive" %>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No accounts found for this role.</p>
    <% } %>
    <button class="button_1" onclick="location.href='dashboard.jsp'">Back to Dashboard</button>
</div>
</body>
</html>