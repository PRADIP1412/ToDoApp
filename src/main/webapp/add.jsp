<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Todo</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(to right, red, blue);
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        color: white;
    }

    .container {
        background: rgba(255, 255, 255, 0.2);
        padding: 60px;
        border-radius: 10px;
        backdrop-filter: blur(10px);
        text-align: center;
        width: 300px;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    }

    h2 {
        margin-bottom: 15px;
    }

    input, select {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: none;
        border-radius: 5px;
    }

    input[type="text"], input[type="date"], select {
        background: rgba(255, 255, 255, 0.8);
    }

    input[type="submit"] {
        background: #28a745;
        color: white;
        font-size: 16px;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background: #218838;
    }
</style>
</head>
<body>

    <div class="container">
        <h2>Add Todo</h2>
        <form action="addForm" method="post">
		    <label>Title:</label>
		    <input type="text" name="title" required><br>
		    <label>Target Date:</label>
		    <input type="date" name="targetDate" required><br>
		    <label>Status:</label>
		   	<select name="status">
		   		<option>Pending</option>
		   		<option>Complete</option>
		   	</select>
		    <input type="submit" value="Add Todo">
		</form>

    </div>

</body>
</html>
