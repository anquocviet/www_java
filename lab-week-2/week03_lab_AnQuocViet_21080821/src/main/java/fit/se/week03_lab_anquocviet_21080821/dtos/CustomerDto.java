package fit.se.week03_lab_anquocviet_21080821.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.week03_lab_anquocviet_21080821.models.Customer}
 */
public record CustomerDto(
      @NotNull
      long id,

      @NotNull
      @NotBlank
      String name,

      @NotNull
      @NotBlank
      @Email
      String email,

      @NotNull
      @NotBlank
      String phone,

      @NotNull
      @NotBlank
      String address
) implements Serializable {
}