package in.sp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import in.sp.databaseConnection.DatabaseConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addForm")
public class Add extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");


        String title = req.getParameter("title");
        String dateStr = req.getParameter("targetDate"); // Date input
        String status = req.getParameter("status");


        HttpSession session = req.getSession();
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            out.println("<h3 style='color:red'>Session expired. Please log in again.</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
            rd.include(req, resp);
            return;
        }

        try {
            
            Date targetDate = Date.valueOf(dateStr);
            
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO todos (title, target_date, status, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, title);
            ps.setDate(2, targetDate);
            ps.setString(3, status);
            ps.setInt(4, user_id);

            int count = ps.executeUpdate();

            if (count > 0) {
                RequestDispatcher rd = req.getRequestDispatcher("toDo.jsp");
                rd.forward(req, resp);
            } else {
                out.println("<h3 style='color:red'>Add operation failed. Please try again.</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("add.jsp");
                rd.include(req, resp);
            }

        } catch (IllegalArgumentException e) {
            out.println("<h3 style='color:red'>Invalid date format. Please enter a valid date.</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("add.jsp");
            rd.include(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
