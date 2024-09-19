package fit.se.week03_lab_anquocviet_21080821.resources;

import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.services.ProductService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
@Path("/products")
public class ProductResource {
   @Inject
   private ProductService productService;


   @GET
   @Produces("application/json")
   public Set<ProductDto> products() {
      return productService.getAllProducts();
   }

   @GET
   @Path("/{id}")
   @Produces("application/json")
   public ProductDto product(@PathParam("id") int id) {
      return productService.getProductById(id).orElseThrow(
            () -> new EntityNotFoundException("Product with id " + id + " not found")
      );
   }
}


