package fit.se.week03_lab_anquocviet_21080821.resources;

import fit.se.week03_lab_anquocviet_21080821.dtos.CustomerDto;
import fit.se.week03_lab_anquocviet_21080821.services.CustomerService;
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

import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */
@Path("/customers")
public class CustomerResource {
   @Inject
   private CustomerService customerService;

   @GET
   @Produces("application/json")
   public Set<CustomerDto> customers() {
      return customerService.getAllCustomers();
   }

   @GET
   @Path("/{id}")
   @Produces("application/json")
   public CustomerDto customer(@PathParam("id") int id) {
      return customerService.getCustomerById(id);
   }

   @POST
   @Consumes("application/json")
   @Produces("application/json")
   public CustomerDto addCustomer(@Valid CustomerDto customerDto) {
      return customerService.createCustomer(customerDto);
   }

   @PUT
   @Consumes("application/json")
   @Produces("application/json")
   public CustomerDto updateCustomer(@Valid CustomerDto customerDto) {
      return customerService.updateCustomer(customerDto);
   }

   @DELETE
   @Path("/{id}")
   public String deleteCustomer(@PathParam("id") int id) {
      customerService.deleteCustomer(id);
      return "ok";
   }
}
