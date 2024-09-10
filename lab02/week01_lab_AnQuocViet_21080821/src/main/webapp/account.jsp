<%@ page import="java.util.Set" %>
<%@ page import="fit.se.week01_lab_anquocviet_21080821.entities.Role" %>
<%@ page import="fit.se.week01_lab_anquocviet_21080821.entities.Account" %>
<%@ page import="java.util.HashSet" %>
<%--
  Created by IntelliJ IDEA.
  User: vie
  Date: 7/9/24
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Account</title>
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
            width: 450px;
            text-align: center;
        }

        h3 {
            margin-bottom: 20px;
        }

        form {
            width: 95%;
        }

        label {
            display: block;
            margin-bottom: 8px;
            text-align: left;
        }

        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #77aaff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #5599dd;
        }

        .message {
            color: red;
            font-size: 16px;
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
<div class="container">
    <%
        String message = request.getParameter("message");
        Account account = (Account) request.getAttribute("account");
        if (account == null) {
            account = new Account("", "", "", "", "", (byte) 1, null);
        }
        Set<Role> roles = (Set<Role>) request.getAttribute("roles");
        if (roles == null) {
            roles = new HashSet<>();
        }
    %>
    <form action="controller" method="post">
        <h3>Account Form</h3>
        <input type="hidden" name="account_id" id="account_id"
               value="<%=account.getAccountId()%>">
        <label for="full_name">Full Name: </label>
        <input type="text" name="full_name" id="full_name" placeholder="Full Name"
               value="<%=account.getFullName()%>">
        <label for="password">Password: </label>
        <input type="password" name="password" id="password" placeholder="Password"
               value="<%=account.getPassword()%>">
        <label for="email">Email: </label>
        <input type="email" name="email" id="email" placeholder="Email"
               value="<%=account.getEmail()%>">
        <label for="phone">Phone: </label>
        <input type="text" name="phone" id="phone" placeholder="Phone"
               value="<%=account.getPhone()%>">
        <label>Status</label>
        <div style="text-align: start">
            <label>
                <input type="radio" name="status" value="1" checked>
                Active
            </label>
            <label>
                <input type="radio" name="status" value="0"
                    <%=account.getStatus() == 0 ? "checked" : ""%>>
                Inactive
            </label>
        </div>
        <br>
        <label>Roles:</label>
        <div style="text-align: start">
            <%
                for (Role r : roles) {
            %>
            <label>
                <input type="checkbox" name="role" value="<%=r.getRoleId()%>"
                    <%=account.getGrantAccesses().stream().anyMatch(
                            g -> g.getRole().getRoleId().equalsIgnoreCase(r.getRoleId())
                    ) ? "checked" : ""%>>
                <%=r.getRoleName()%>
            </label>
            <%
                }
            %>
        </div>
        <br>
        <div class="message"><%=message != null ? message : ""%>
        </div>
        <br>
        <button type="submit" name="action" value="add-account" class="button_1">Add Account</button>
        <button type="submit" name="action" value="update-account" class="button_1">Update Account</button>
        <button type="submit" name="action" value="delete-account" class="button_1">Delete Account</button>
    </form>
</div>
</body>
</html>