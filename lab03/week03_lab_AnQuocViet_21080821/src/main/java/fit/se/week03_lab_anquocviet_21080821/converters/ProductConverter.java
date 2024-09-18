package fit.se.week03_lab_anquocviet_21080821.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.models.Product;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
public class ProductConverter {
   private static final ObjectMapper om = new ObjectMapper();

   public static ProductDto convertToDto(Product product) {
      return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getUnit(),
            product.getManufacturer(),
            product.getStatus()
      );
   }
}
