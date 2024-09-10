<%@ page import="fit.se.week01_lab_anquocviet_21080821.entities.Role" %>
<%--
Created by IntelliJ IDEA.
User: vie
Date: 9/9/24
Time: 18:11
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Role</title>
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
            width: 400px;
            text-align: center;
        }

        h3 {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            text-align: left;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        button {
            background-color: #77aaff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }

        button:hover {
            background-color: #5599dd;
        }
    </style>
</head>
<body>
<%
    Role role = (Role) request.getAttribute("role");
    if (role == null) {
        role = new Role("", "", "", (byte) 1, null);
    }
%>


<div class="container">
    <h3>Role</h3>
    <form action="controller" method="post">
        <input type="hidden" name="role_id" value="<%=role.getRoleId()%>">
        <label for="name">Name</label>
        <input type="text" id="name" name="role_name" required
               value="<%=role.getRoleName()%>">
        <label for="description">Description</label>
        <input type="text" id="description" name="description" value="<%=role.getDescription()%>" required>
        <label>Status</label>
        <div style="text-align: start">
            <input type="radio" id="use" name="status" value="1" <%=role.getStatus()==1 ? "checked" : ""%>>
            <label style="display: inline-block" for="use">Using</label>
            <input type="radio" id="not-use" name="status" value="0" <%=role.getStatus()==0 ? "checked" : ""%>>
            <label style="display: inline-block" for="not-use">Not Using</label>
        </div>
        <button type="submit" name="action" value="add-role">Add Role</button>
        <button type="submit" name="action" value="update-role">Update Role</button>
        <button type="submit" name="action" value="delete-role">Delete Role</button>
    </form>
</div>
</body>
</html>