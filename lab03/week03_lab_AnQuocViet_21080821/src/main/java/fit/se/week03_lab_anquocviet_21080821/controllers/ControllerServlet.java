package fit.se.week03_lab_anquocviet_21080821.controllers;

import fit.se.week03_lab_anquocviet_21080821.services.CustomerService;
import fit.se.week03_lab_anquocviet_21080821.services.EmployeeService;
import fit.se.week03_lab_anquocviet_21080821.services.ProductService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 21/9/24
 */
@WebServlet(name = "controller", value = "/controller")
public class ControllerServlet extends HttpServlet {
   @Inject
   private ProductService productService;
   @Inject
   private CustomerService customerService;
   @Inject
   private EmployeeService employeeService;
   Logger logger = Logger.getLogger(ControllerServlet.class.getName());


   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String action = req.getParameter("action");
      action = action == null ? "" : action.toLowerCase();
      switch (action) {
         case "buy" -> buy(req, resp);
         default -> resp.sendRedirect("index.jsp");
      }
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String action = req.getParameter("action");
      action = action == null ? "" : action.toLowerCase();
      switch (action) {
         case "pay" -> pay(req, resp);
         default -> resp.sendRedirect("index.jsp");
      }
   }

   private void pay(HttpServletRequest req, HttpServletResponse resp) {
      String customerId = req.getParameter("customerId");
      String employeeId = req.getParameter("employeeId");
      Set<Long> productIds =
            Set.of(req.getParameterValues("productId"))
                  .stream()
                  .map(Long::parseLong)
                  .collect(Collectors.toSet());
      logger.info("Customer ID: " + customerId);
      logger.info("Employee ID: " + employeeId);
      logger.info("Product IDs: " + productIds);
   }

   private void buy(HttpServletRequest req, HttpServletResponse resp) {
      Set<Long> productIds =
            Set.of(req.getParameterValues("productId"))
                  .stream()
                  .map(Long::parseLong)
                  .collect(Collectors.toSet());

      req.setAttribute("products", productService.getProductsByIds(productIds));
      req.setAttribute("customers", customerService.getAllCustomers());
      req.setAttribute("employees", employeeService.getAllEmployees());
      try {
         req.getRequestDispatcher("cart.jsp").forward(req, resp);
      } catch (ServletException | IOException e) {
         logger.severe(e.getMessage());
      }
   }
}
