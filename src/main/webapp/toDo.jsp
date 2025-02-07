<%@page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Connection" %>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="in.sp.databaseConnection.DatabaseConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ToDo List</title>
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
    h2 {
        margin-bottom: 20px;
    }
    button {
        background: white;
        color: #764ba2;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        font-size: 1rem;
        font-weight: bold;
        cursor: pointer;
        transition: 0.3s;
        margin-bottom: 20px;
    }
    button a {
        text-decoration: none;
        color: #764ba2;
    }
    button:hover {
        background: #764ba2;
        color: white;
    }
    button:hover a {
        color: white;
    }
    table {
        width: 80%;
        border-collapse: collapse;
        background: rgba(255, 255, 255, 0.2);
        border-radius: 10px;
        overflow: hidden;
    }
    th, td {
        padding: 12px;
        border: 1px solid white;
    }
    th {
        background: rgba(255, 255, 255, 0.3);
    }
    td a {
        text-decoration: none;
        color: white;
        margin: 0 10px;
    }
    td a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>

<h2>List Of Todos</h2>

<button><a href="add.jsp">Add Todo</a></button>

<table>
    <tr>
        <th>Title</th>
        <th>Target Date</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>

    <%
    Integer user_id = (Integer) session.getAttribute("user_id"); 
    
    if (user_id == null) {
        response.sendRedirect("login.jsp"); 
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM todos WHERE user_id = ?"; 
        ps = conn.prepareStatement(query);
        ps.setInt(1, user_id);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String targetDate = rs.getString("target_date");
            String status = rs.getString("status");
    %>
        <tr>
            <td><%= title %></td>
            <td><%= targetDate %></td>
            <td><%= status %></td>
            <td>
   		<a href="edit.jsp?id=<%= id %>">Edit</a>
    		<a href="DeleteServlet?id=<%= id %>" onclick="return confirm('Are you sure you want to delete this task?')">Delete</a>
	    </td>
        </tr>
    <%
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
    %>

</table>

</body>
</html>
