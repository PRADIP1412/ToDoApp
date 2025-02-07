package in.sp.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.sp.databaseConnection.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/editForm")
public class Edit extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get the form data
            int id = Integer.parseInt(req.getParameter("id"));
            String title = req.getParameter("title");
            String targetDate = req.getParameter("targetDate"); // Format: YYYY-MM-DD
            String status = req.getParameter("status");
            
            HttpSession session = req.getSession();
            Integer user_id = (Integer) session.getAttribute("user_id");
            

            // Establish a database connection
            Connection conn = DatabaseConnection.getConnection();

            // SQL query to update the To-Do item
            String query = "UPDATE todos SET title = ?, target_date = ?, status = ? WHERE id = ? and user_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, targetDate);
            ps.setString(3, status);
            ps.setInt(4, id);
            ps.setInt(5, user_id);

            // Execute update
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Todo updated successfully.");
            } else {
                System.out.println("Error: Todo not updated.");
            }

            // Redirect back to the To-Do list page (change the URL as per your project)
            resp.sendRedirect("toDo.jsp");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.getWriter().println("Invalid ID format.");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().println("Database error.");
        }
    }
}
