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

        // Retrieve form data
        String title = req.getParameter("title");
        String dateStr = req.getParameter("targetDate"); // Date input
        String status = req.getParameter("status");

        // Retrieve user_id from session
        HttpSession session = req.getSession();
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            out.println("<h3 style='color:red'>Session expired. Please log in again.</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
            rd.include(req, resp);
            return;
        }

        try {
            // Convert the date string to java.sql.Date
            Date targetDate = Date.valueOf(dateStr);

            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();

            // Debugging: Print SQL values before execution
//            System.out.println("Title: " + title);
//            System.out.println("Target Date: " + targetDate);
//            System.out.println("Status: " + status);
//            System.out.println("User ID: " + user_id);

            // Insert query (Ensure the table column names are correct)
            String query = "INSERT INTO todos (title, target_date, status, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set the parameters in the PreparedStatement
            ps.setString(1, title);
            ps.setDate(2, targetDate);
            ps.setString(3, status);
            ps.setInt(4, user_id);

            // Execute the query
            int count = ps.executeUpdate();

            if (count > 0) {
                // Successfully inserted, redirect to toDo.jsp
                RequestDispatcher rd = req.getRequestDispatcher("toDo.jsp");
                rd.forward(req, resp);
            } else {
                // If insert fails, show an error message
                out.println("<h3 style='color:red'>Add operation failed. Please try again.</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("add.jsp");
                rd.include(req, resp);
            }

        } catch (IllegalArgumentException e) {
            // Handle invalid date format
            out.println("<h3 style='color:red'>Invalid date format. Please enter a valid date.</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("add.jsp");
            rd.include(req, resp);
        } catch (Exception e) {
            // Catch any exceptions and print the stack trace
            e.printStackTrace();
            out.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
