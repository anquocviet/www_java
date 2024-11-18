package fit.se.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.backend.models.Company}
 */
public record CreateCompanyDTO(
      @NotEmpty
      String about,

      @Email
      String email,

      @NotEmpty
      String password,

      @NotEmpty
      String compName,

      @NotEmpty
      String phone,

      @NotEmpty
      String webUrl,

      AddressDto address
) implements Serializable {
}