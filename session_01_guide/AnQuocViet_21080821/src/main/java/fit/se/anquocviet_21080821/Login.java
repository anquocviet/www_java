package fit.se.anquocviet_21080821;

import fit.se.anquocviet_21080821.beans.AuthBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description
 * @author: vie
 * @date: 24/8/24
 */
@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String username = req.getParameter("username");
      String password = req.getParameter("password");
      try (PrintWriter out = resp.getWriter()) {

         if (username == null || password == null) {
            out.println("<html><h1>Login fail</h1>" +
                              "<br>" +
                              "<h1>Username or password is require</h1></html>");
            return;
         }
         if (username.isEmpty() || password.isEmpty()) {
            out.println("<html><h1>Login fail</h1>" +
                              "<br>" +
                              "<h1>Username or password is require</h1></html>");
            return;
         }

         AuthBean authBean = new AuthBean();
         if (authBean.login(username, password)) {
            resp.setStatus(200);
            out.println("<html><h1>Login success</h1>" +
                              "<br>" +
                              "<h1>Welcome " + username + " to website</h1></html>");

         } else {
            out.println("<html><h1>Login fail</h1>" +
                              "<br>" +
                              "<h1>Username or password is wrong</h1></html>");
         }
      } catch (IOException e) {
         e.printStackTrace();
      }


   }
}
