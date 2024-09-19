package fit.se.week03_lab_anquocviet_21080821.resources;

import fit.se.week03_lab_anquocviet_21080821.dtos.EmployeeDto;
import fit.se.week03_lab_anquocviet_21080821.services.EmployeeService;
import jakarta.inject.Inject;
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
@Path("/employees")
public class EmployeeResource {
   @Inject
   private EmployeeService employeeService;

   @GET
   @Produces("application/json")
   public Set<EmployeeDto> employees() {
      return employeeService.getAllEmployees();
   }

   @GET
   @Path("/{id}")
   @Produces("application/json")
   public EmployeeDto employeeById(@PathParam("id") int id) {
      return employeeService.getEmployeeById(id);
   }

   @POST
   @Consumes("application/json")
   @Produces("application/json")
   public EmployeeDto addEmployee(EmployeeDto employeeDto) {
      return employeeService.createEmployee(employeeDto);
   }

   @PUT
   @Consumes("application/json")
   @Produces("application/json")
   public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
      return employeeService.updateEmployee(employeeDto);
   }

   @DELETE
   @Path("/{id}")
   public String deleteEmployee(@PathParam("id") int id) {
      employeeService.deleteEmployee(id);
      return "ok";
   }
}
