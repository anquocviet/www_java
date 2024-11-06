package fit.se.backend.dtos;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.backend.models.Company}
 */
public record CompanyDto(
      Long id,
      String about,
      String email,
      String compName,
      String phone,
      String webUrl,
      AddressDto address
) implements Serializable {
}