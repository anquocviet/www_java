package fit.se.week03_lab_anquocviet_21080821.dtos;

import fit.se.week03_lab_anquocviet_21080821.models.ProductPrice;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ProductPrice}
 */
public record ProductPriceDto(
      LocalDateTime priceDateTime,
      double price,
      String note
) implements Serializable {
}