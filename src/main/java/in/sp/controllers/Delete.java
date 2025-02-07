package in.sp.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.sp.databaseConnection.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DeleteServlet")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get session and validate user
        HttpSession session = req.getSession();
        Integer user_id = (Integer) session.getAttribute("user_id"); // Get user_id from session
        
        if (user_id == null) {
            resp.sendRedirect("login.jsp"); // Redirect if user is not logged in
            return;
        }

        // Get todo id from request
        int todo_id = Integer.parseInt(req.getParameter("id"));
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // Step 1: Check if the to-do belongs to the logged-in user
            String checkQuery = "SELECT user_id FROM todos WHERE id = ?";
            ps = conn.prepareStatement(checkQuery);
            ps.setInt(1, todo_id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int db_user_id = rs.getInt("user_id");
                
                if (db_user_id != user_id) {
                    // If the to-do does not belong to the logged-in user, deny access
                    resp.getWriter().write("Unauthorized access!");
                    return;
                }
            } else {
                resp.getWriter().write("Todo not found!");
                return;
            }
            
            // Step 2: Delete the todo
            String deleteQuery = "DELETE FROM todos WHERE id = ? AND user_id = ?";
            ps = conn.prepareStatement(deleteQuery);
            ps.setInt(1, todo_id);
            ps.setInt(2, user_id);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                resp.sendRedirect("toDo.jsp"); // Redirect to the to-do list
            } else {
                resp.getWriter().write("Failed to delete todo!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
