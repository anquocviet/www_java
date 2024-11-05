package fit.se.week03_lab_anquocviet_21080821.dtos;

import fit.se.week03_lab_anquocviet_21080821.enums.ProductStatus;
import fit.se.week03_lab_anquocviet_21080821.models.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.week03_lab_anquocviet_21080821.models.Product}
 */
public record ProductDto(
      @NotNull(message = "Product ID cannot be null")
      long id,

      @NotNull(message = "Product name cannot be null")
      @NotBlank(message = "Product name cannot be blank")
      String name,

      @NotNull(message = "Product description cannot be null")
      @NotBlank(message = "Product description cannot be blank")
      String description,

      @NotNull(message = "Product unit cannot be null")
      @NotBlank(message = "Product unit cannot be blank")
      String unit,

      @NotNull(message = "Product manufacturer cannot be null")
      @NotBlank(message = "Product manufacturer cannot be blank")
      String manufacturer,

      @NotNull(message = "Product status cannot be null")
      ProductStatus status,

      @NotNull(message = "Product price cannot be null")
      ProductPriceDto price,

      @NotNull(message = "Product image cannot be null")
      ProductImageDto image
) implements Serializable {
}