<%--
  Created by IntelliJ IDEA.
  User: vie
  Date: 8/9/24
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page
        contentType="text/html;charset=UTF-8"
        language="java"
        errorPage="error.jsp"
        isErrorPage="true"
%>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <p><%=exception.toString()%></p>
</body>
</html>
