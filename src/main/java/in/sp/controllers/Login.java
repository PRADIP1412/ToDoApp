package in.sp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.sp.databaseConnection.DatabaseConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginForm")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE name=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Retrieve user details
                int user_id = rs.getInt("user_id");
                String userEmail = rs.getString("email");

                // Start session and save user_id & username
                HttpSession session = req.getSession();
                session.setAttribute("user_id", user_id);
                session.setAttribute("username", username);
                session.setAttribute("email", userEmail);

                // Redirect to ToDo page
                resp.sendRedirect("toDo.jsp");
            } else {
                // Login failed, show error message
                out.print("<h3 style='color:red'>Username or password incorrect</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
                rd.include(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red'>An error occurred, please try again.</h3>");
        }
    }
}
