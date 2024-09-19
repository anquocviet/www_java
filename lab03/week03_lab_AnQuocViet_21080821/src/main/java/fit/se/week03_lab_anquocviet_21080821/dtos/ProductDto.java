package fit.se.week03_lab_anquocviet_21080821.dtos;

import fit.se.week03_lab_anquocviet_21080821.enums.ProductStatus;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link fit.se.week03_lab_anquocviet_21080821.models.Product}
 */
public record ProductDto(
      long id,
      String name,
      String description,
      String unit,
      String manufacturer,
      ProductStatus status,
      Set<ProductPriceDto> prices,
      Set<ProductImageDto> images
) implements Serializable {
}