package fit.se.week03_lab_anquocviet_21080821.resources;

import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductImageDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductPriceDto;
import fit.se.week03_lab_anquocviet_21080821.services.ProductService;
import jakarta.ejb.EJB;
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
   @EJB
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

   @POST
   @Path("/update-price/{productId}")
   @Consumes("application/json")
   @Produces("application/json")
   public ProductDto updateProductPrice(@PathParam("productId") long productId, ProductPriceDto productPriceDto) {
      return productService.updateProductPrice(productId, productPriceDto);
   }

   @POST
   @Path("/update-image/{productId}")
   @Consumes("application/json")
   @Produces("application/json")
   public ProductDto updateProductImage(@PathParam("productId") long productId, ProductImageDto productImageDto) {
      return productService.updateProductImage(productId, productImageDto);
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


