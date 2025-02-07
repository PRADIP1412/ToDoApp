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
            int id = Integer.parseInt(req.getParameter("id"));
            String title = req.getParameter("title");
            String targetDate = req.getParameter("targetDate"); 
            String status = req.getParameter("status");
            
            HttpSession session = req.getSession();
            Integer user_id = (Integer) session.getAttribute("user_id");
            
            Connection conn = DatabaseConnection.getConnection();
            String query = "UPDATE todos SET title = ?, target_date = ?, status = ? WHERE id = ? and user_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, targetDate);
            ps.setString(3, status);
            ps.setInt(4, id);
            ps.setInt(5, user_id);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Todo updated successfully.");
            } else {
                System.out.println("Error: Todo not updated.");
            }
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
