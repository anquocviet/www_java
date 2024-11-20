package fit.se.backend.dtos;

import fit.se.backend.models.Candidate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link Candidate}
 */
public record CandidateDto(
      Long id,

      @NotNull(message = "Date of birth is required")
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      LocalDate dob,

      @Email(message = "Invalid email")
      String email,

      @NotNull(message = "Full name is required")
      String fullName,

      @NotNull(message = "Phone number is required")
      String phone,

      AddressDto address,

      Set<CandidateSkillDto> skills
) implements Serializable {
}