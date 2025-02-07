package in.sp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import in.sp.databaseConnection.DatabaseConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/regForm")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String name = req.getParameter("username");
        String email = req.getParameter("emailId");
        String password = req.getParameter("password");

        try {
        	Connection conn = DatabaseConnection.getConnection();
        	if (conn == null) {
        	    out.print("<h3 style='color:red'>Database Connection Failed!</h3>");
        	    return;
        	}
            String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                // Get generated user_id
                var rs = ps.getGeneratedKeys();
                int user_id = -1;
                if (rs.next()) {
                    user_id = rs.getInt(1);
                }

                // Store user session
                HttpSession session = req.getSession();
                session.setAttribute("user_id", user_id);
                session.setAttribute("username", name);
                session.setAttribute("email", email);

                // Redirect to ToDo page
                resp.sendRedirect("login.jsp");
            } else {
                out.print("<h3 style='color:red'>Registration failed. Try again.</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
                rd.include(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print in the console
            out.print("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>"); // Show error in browser
        }

    }
}
