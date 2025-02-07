<%@page import="java.sql.Date"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="in.sp.databaseConnection.DatabaseConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Todo</title>
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
        <%
        String idParam = request.getParameter("id");
        int id = 0;
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                out.println("<p style='color:red;'>Invalid ID</p>");
            }
        }

        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM todos WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        String title = "";
        Date targetDate = null;
        String status = "";

        if (rs.next()) {
            title = rs.getString("title");
            targetDate = rs.getDate("target_date");
            status = rs.getString("status");
        }
        %>
        
        <h2>Edit Todo</h2>
        <form action="editForm" method="post">
            <input type="hidden" name="id" value="<%= id %>">
            
            <label>Title:</label>
            <input type="text" name="title" value="<%= title %>" required><br>
            
            <label>Target Date:</label>
            <input type="date" name="targetDate" value="<%= targetDate %>" required><br>
            
            <label>Status:</label>
            <select name="status">
                <option value="Pending" <%= status.equals("Pending") ? "selected" : "" %>>Pending</option>
                <option value="Complete" <%= status.equals("Complete") ? "selected" : "" %>>Complete</option>
            </select>
            
            <input type="submit" value="Update Todo">
        </form>
    </div>

</body>
</html>
