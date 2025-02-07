<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        text-align: center;
        height: 100vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin: 0;
    }
    h1 {
        font-size: 3rem;
        margin-bottom: 10px;
    }
    h2 {
        font-size: 1.8rem;
        margin-bottom: 20px;
    }
    a {
        display: inline-block;
        text-decoration: none;
        background: white;
        color: #764ba2;
        padding: 12px 24px;
        margin: 10px;
        border-radius: 25px;
        font-size: 1.2rem;
        font-weight: bold;
        transition: 0.3s;
    }
    a:hover {
        background: #764ba2;
        color: white;
    }
</style>
</head>
<body>
    <h1>ToDo</h1>
    <h2>Welcome to ToDo App</h2>
    <a href="login.jsp">Login</a>
    <a href="register.jsp">Register</a>
</body>
</html>