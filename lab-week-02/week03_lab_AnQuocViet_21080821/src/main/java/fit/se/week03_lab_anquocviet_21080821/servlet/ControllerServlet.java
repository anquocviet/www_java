package fit.se.week03_lab_anquocviet_21080821.servlet;

import fit.se.week03_lab_anquocviet_21080821.dtos.CreateOrderDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.services.CustomerService;
import fit.se.week03_lab_anquocviet_21080821.services.EmployeeService;
import fit.se.week03_lab_anquocviet_21080821.services.OrderService;
import fit.se.week03_lab_anquocviet_21080821.services.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 30/9/24
 */
@WebServlet(name = "ControllerServlet", urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {
   @EJB
   private ProductService productService;
   @EJB
   private EmployeeService employeeService;
   @EJB
   private OrderService orderService;
   @EJB
   private CustomerService customerService;


   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String action = req.getParameter("action");
      action = action.toLowerCase();

      switch (action) {
         case "buy" -> {
            Map<String, String[]> parameterMap = req.getParameterMap();
            String[] productIds = parameterMap.get("productId");
            Set<Long> productIdsSet = new HashSet<>(Arrays.stream(productIds).map(Long::parseLong).toList());
            Set<ProductDto> products = productService.getProductsByIds(productIdsSet);
            req.setAttribute("products", products);
            req.getRequestDispatcher("cart.jsp").forward(req, resp);
         }
         default -> {
            resp.sendRedirect("index.jsp");
         }
      }
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String action = req.getParameter("action");
      action = action.toLowerCase();

      switch (action) {
         case "buy" -> {
            Map<String, String[]> parameterMap = req.getParameterMap();
            String employeeId = parameterMap.get("employeeId")[0];
            String customerId = parameterMap.get("customerId")[0];
            String[] productIds = parameterMap.get("productId");
            String[] quantities = parameterMap.get("quantity");

            Set<CreateOrderDto.CreateOrderDetailDto> orderDetailDtos = new HashSet<>();
            for (int i = 0; i < productIds.length; i++) {
               CreateOrderDto.CreateOrderDetailDto createOrderDetailDto = new CreateOrderDto.CreateOrderDetailDto(
                     Double.parseDouble(quantities[i]),
                     "",
                     Long.parseLong(productIds[i])
               );
               orderDetailDtos.add(createOrderDetailDto);
            }
            CreateOrderDto createOrderDto = new CreateOrderDto(
                  LocalDateTime.now(),
                  Long.parseLong(customerId),
                  Long.parseLong(employeeId),
                  orderDetailDtos
            );
            orderService.createOrder(createOrderDto);
            req.getSession().setAttribute("message", "Order created successfully");
            resp.sendRedirect("index.jsp");
         }
         default -> {
            resp.sendRedirect("index.jsp");
         }
      }
   }
}
