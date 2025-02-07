<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        
        height: 100vh;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin: 0;
    }
    form {
        background: rgba(255, 255, 255, 0.2);
        padding-top : 30px;
        padding-right : 70px;
        padding-bottom : 50px;
        padding-left: 50px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: none;
        border-radius: 5px;
        font-size: 1rem;
    }
    input[type="submit"] {
        background: white;
        color: #764ba2;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        font-size: 1.2rem;
        font-weight: bold;
        cursor: pointer;
        transition: 0.3s;
    }
    input[type="submit"]:hover {
        background: #764ba2;
        color: white;
    }
    #a{
    	text-align : center;
    }
    #btn{
    	text-align : center;
    }
</style>
</head>
<body>
    <form action="loginForm" method="post">
        <h2 id="a">Login</h2>
        <label>Enter Username:</label>
        <input type="text" name="username" required><br>
        <label>Enter Password:</label>
        <input type="password" name="password" required><br>
        <input type="submit" value="Login" id="btn">
    </form>
</body>
</html>