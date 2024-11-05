<%@ page import="java.util.Set" %>
<%@ page import="fit.se.week01_lab_anquocviet_21080821.entities.Account" %>
<%@ page import="fit.se.week01_lab_anquocviet_21080821.entities.Role" %>
<%--Created by IntelliJ IDEA.
User: vie
Date: 8/9/24
Time: 16:08
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }

        header {
            background: #333;
            color: #fff;
            padding-top: 30px;
            min-height: 70px;
            border-bottom: #77aaff 3px solid;
        }

        header a {
            color: #fff;
            text-decoration: none;
            text-transform: uppercase;
            font-size: 16px;
        }

        header ul {
            padding: 0;
            list-style: none;
        }

        header li {
            float: left;
            display: inline;
            padding: 0 20px 0 20px;
        }

        header #branding {
            float: left;
        }

        header #branding h1 {
            margin: 0;
        }

        header nav {
            float: right;
            margin-top: 10px;
        }

        header .highlight, header .current a {
            color: #77aaff;
            font-weight: bold;
        }

        header a:hover {
            color: #77aaff;
            font-weight: bold;
        }

        .button_1 {
            height: 38px;
            background: #77aaff;
            border: 0;
            padding-left: 20px;
            padding-right: 20px;
            color: #fff;
            cursor: pointer;
        }

        table {
            width: 100%;
            margin: 20px 0;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .message {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<header>
    <div class="container">
        <div id="branding">
            <h1>Dashboard</h1>
        </div>
        <nav>
            <ul>
                <li><a href="controller?action=logout">Logout</a></li>
            </ul>
        </nav>
    </div>
</header>
<%
    Account account = (Account) session.getAttribute("account");
    Set<Account> accounts = (Set<Account>) session.getAttribute("accounts");
    Set<Role> roles = (Set<Role>) session.getAttribute("roles");
    String message = (String) request.getAttribute("message");
%>
<%
    if (account == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<div class="container">
    <h1>Welcome, <%=account.getFullName()%>
    </h1>
    <p>Email: <%=account.getEmail()%>
    </p>
    <p>Phone: <%=account.getPhone()%>
    </p>
    <p>Permission of user: <%=
    account.getGrantAccesses()
            .stream()
            .map(g -> g.getRole().getRoleName())
            .reduce((a, b) -> a + ", " + b)
            .orElse("")
    %>
    </p>
    <button class="button_1" onclick="location.href='controller?action=logout'">Logout</button>

    <% if (message != null) { %>
    <p class="message"><%=message%>
    </p>
    <% } %>

    <% if (accounts != null) { %>
    <h2>List of accounts</h2>
    <table>
        <tr>
            <th>Full name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Status</th>
            <th>Roles</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <% for (Account a : accounts) { %>
        <tr>
            <td>
                <%=a.getFullName()%>
            </td>
            <td>
                <%=a.getEmail()%>
            </td>
            <td>
                <%=a.getPhone()%>
            </td>
            <td>
                <%=a.getStatus() == 1 ? "Active" : "Inactive"%>
            </td>
            <td>
                <%=a.getGrantAccesses()
                           .stream()
                           .map(g -> g.getRole().getRoleName())
                           .reduce((b, c) -> b + ", " + c)
                           .orElse("")
                %>
            </td>
            <td>
                <form action="controller">
                    <input type="hidden" name="action" value="redirect">
                    <input type="hidden" name="page" value="account.jsp">
                    <input type="hidden" name="account_id" value="<%=a.getAccountId()%>">
                    <input class="button_1" type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form action="controller" method="post">
                    <input type="hidden" name="action" value="delete-account">
                    <input type="hidden" name="account_id" value="<%=a.getAccountId()%>">
                    <input class="button_1" type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <form action="account.jsp">
        <input class="button_1" type="submit" value="Add Account">
    </form>
    <% } %>

    <% if (roles != null) { %>
    <h2>List Role</h2>
    <table>
        <tr>
            <th>Role Name</th>
            <th>Role Description</th>
            <th>Status</th>
            <th>Accounts</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <% for (Role r : roles) { %>
        <tr>
            <td><%=r.getRoleName()%>
            </td>
            <td><%=r.getDescription()%>
            </td>
            <td><%=r.getStatus() == 1 ? "Active" : "Inactive"%>
            </td>
            <td>
                <a style="display: inline-flex; text-decoration: none; align-items: center" class="button_1"
                   href="controller?action=redirect&page=account-of-role.jsp&role=<%=r.getRoleId()%>">View Accounts</a>
            </td>
            <td>
                <form action="controller">
                    <input type="hidden" name="action" value="redirect">
                    <input type="hidden" name="page" value="role.jsp">
                    <input type="hidden" name="role_id" value="<%=r.getRoleId()%>">
                    <input class="button_1" type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form action="controller" method="post">
                    <input type="hidden" name="action" value="delete-role">
                    <input type="hidden" name="role_id" value="<%=r.getRoleId()%>">
                    <input class="button_1" type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <form action="role.jsp">
        <input class="button_1" type="submit" value="Add Role">
    </form>
    <% } %>
</div>
</body>
</html>