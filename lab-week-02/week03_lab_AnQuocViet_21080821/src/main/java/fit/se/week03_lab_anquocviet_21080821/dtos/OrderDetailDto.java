package fit.se.week03_lab_anquocviet_21080821.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.week03_lab_anquocviet_21080821.models.OrderDetail}
 */
public record OrderDetailDto(
      @NotNull
      double price,

      @NotNull
      double quantity,

      @NotNull
      @NotBlank
      String note,

      ProductDto product
) implements Serializable {
}