package fit.se.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link fit.se.backend.models.Candidate}
 */
public record CreateCandidateDTO(
      @NotNull
      LocalDate dob,

      @Email
      String email,

      @NotEmpty
      String fullName,

      @NotEmpty
      String phone,

      @NotEmpty
      String password,

      AddressDto address
) implements Serializable {
}