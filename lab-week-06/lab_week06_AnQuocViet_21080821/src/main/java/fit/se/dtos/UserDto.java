package fit.se.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link fit.se.entities.User}
 */
public record UserDto(
      Long id,
      @Size(max = 50) String firstName,
      @Size(max = 50) String middleName,
      @Size(max = 50) String lastName,
      @Size(max = 15) String mobile,
      @Size(max = 50) String email,
      @NotNull @Size(max = 32) String passwordHash,
      @NotNull Instant registeredAt,
      Instant lastLogin,
      String intro,
      String profile
) implements Serializable {
}