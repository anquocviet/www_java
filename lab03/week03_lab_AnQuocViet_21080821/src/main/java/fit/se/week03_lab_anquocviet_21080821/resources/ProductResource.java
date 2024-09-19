package fit.se.week03_lab_anquocviet_21080821.resources;

import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.services.ProductService;
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
      return productService.getProductById(id);
   }

   @POST
   @Consumes("application/json")
   @Produces("application/json")
   public ProductDto addProduct(@Valid ProductDto productDto) {
      return productService.createProduct(productDto);
   }

   @PUT
   @Consumes("application/json")
   @Produces("application/json")
   public ProductDto updateProduct(@Valid ProductDto productDto) {
      return productService.updateProduct(productDto);
   }

   @DELETE
   @Path("/{id}")
   public String deleteProduct(@PathParam("id") int id) {
      productService.deleteProduct(id);
      return "ok";
   }
}

