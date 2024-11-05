package fit.se.week03_lab_anquocviet_21080821.dtos;

import fit.se.week03_lab_anquocviet_21080821.models.ProductPrice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ProductPrice}
 */
public record ProductPriceDto(
      @NotNull
      LocalDateTime priceDateTime,

      @NotNull
      double price,

      @NotNull
      @NotBlank
      String note
) implements Serializable {
}