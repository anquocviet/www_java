package fit.se.week03_lab_anquocviet_21080821.dtos;

import fit.se.week03_lab_anquocviet_21080821.models.ProductImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link ProductImage}
 */
public record ProductImageDto(
      @NotNull
      @NotBlank
      String path,

      @NotNull
      @NotBlank
      String alternative
) implements Serializable {
}