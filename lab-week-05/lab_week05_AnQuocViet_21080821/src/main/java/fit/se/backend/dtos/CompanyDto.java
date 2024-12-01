package fit.se.backend.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.backend.models.Company}
 */
public record CompanyDto(
      @NotNull
      Long id,

      @NotNull
      String about,

      @NotNull
      String email,

      @NotNull
      String compName,

      @NotNull
      String phone,

      String webUrl,

      @NotNull
      AddressDto address
) implements Serializable {
}