package fit.se.week03_lab_anquocviet_21080821.resources;

import fit.se.week03_lab_anquocviet_21080821.dtos.CreateOrderDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.OrderDto;
import fit.se.week03_lab_anquocviet_21080821.services.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */
@Path("/orders")
public class OrderResource {
   @Inject
   private OrderService orderService;

   @GET
   @Produces("application/json")
   public Set<OrderDto> orders() {
      return orderService.getAllOrders();
   }

   @GET
   @Path("/{id}")
   @Produces("application/json")
   public OrderDto orderById(@PathParam("id") int id) {
      return orderService.getOrderById(id);
   }

   @GET
   @Consumes("application/json")
   @Produces("application/json")
   @Path("/statistics-by-day")
   public String statisticsByDay(@QueryParam("date") String date) {
      return orderService.statisticsByDate(date);
   }

   @GET
   @Produces("application/json")
   @Consumes("application/json")
   @Path("/statistics-between-date")
   public String statisticsBetweenDate(@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate) {
      return orderService.statisticsBetweenDates(fromDate, toDate);
   }

   @GET
   @Produces("application/json")
   @Consumes("application/json")
   @Path("/statistics-by-employee-between-date")
   public String statisticsByEmployeeBetweenDate(
         @QueryParam("employeeId") long employeeId,
         @QueryParam("fromDate") String fromDate,
         @QueryParam("toDate") String toDate
   ) {
      return orderService.statisticsByEmployeeBetweenDates(employeeId, fromDate, toDate);
   }


   @POST
   @Consumes("application/json")
   @Produces("application/json")
   public OrderDto createOrder(@Valid CreateOrderDto orderDto) {
      return orderService.createOrder(orderDto);
   }

   @PUT
   @Consumes("application/json")
   @Produces("application/json")
   public OrderDto updateOrder(OrderDto orderDto) {
      return orderService.updateOrder(orderDto);
   }

   @DELETE
   @Path("/{id}")
   public String deleteOrder(@PathParam("id") int id) {
      orderService.deleteOrder(id);
      return "ok";
   }
}
