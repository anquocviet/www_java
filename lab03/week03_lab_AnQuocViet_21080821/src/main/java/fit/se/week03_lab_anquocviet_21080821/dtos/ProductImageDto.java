package fit.se.week03_lab_anquocviet_21080821.dtos;

import fit.se.week03_lab_anquocviet_21080821.models.ProductImage;

import java.io.Serializable;

/**
 * DTO for {@link ProductImage}
 */
public record ProductImageDto(
      long id,
      String path,
      String alternative
) implements Serializable {
}