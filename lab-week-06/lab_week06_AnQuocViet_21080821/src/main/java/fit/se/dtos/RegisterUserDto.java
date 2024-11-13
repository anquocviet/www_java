package fit.se.dtos;

import fit.se.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record RegisterUserDto(
      @Size(max = 50) String firstName,
      @Size(max = 50) String middleName,
      @Size(max = 50) String lastName,
      @Size(max = 15) String mobile,
      @Size(max = 50) String email,
      @NotNull @Size(max = 32) String password
) implements Serializable {
}