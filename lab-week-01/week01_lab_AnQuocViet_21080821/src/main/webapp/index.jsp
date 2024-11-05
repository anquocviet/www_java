<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>An Quốc Việt - 21080821 - Week02</title>
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
            width: 300px;
            text-align: center;
        }
        h1 {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            text-align: left;
        }
        input[type="text"], input[type="password"] {
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
        form {
            width: 95%;
        }
        input[type="submit"]:hover {
            background-color: #5599dd;
        }
        .message {
            color: red;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Login</h1>
    <form action="controller" method="get">
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" value="0903123456" required>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="123" required>
        <input type="submit" name="action" value="Login">
        <p class="message">
            <%= request.getAttribute("message") == null ? "" : "Sai tài khoản hoặc mật khẩu" %>
        </p>
    </form>
</div>
</body>
</html>