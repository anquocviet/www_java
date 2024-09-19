package fit.se.week03_lab_anquocviet_21080821.dtos;

import fit.se.week03_lab_anquocviet_21080821.enums.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link fit.se.week03_lab_anquocviet_21080821.models.Employee}
 */
public record EmployeeDto(
      @NotNull
      long id,

      @NotNull
      @NotBlank
      String fullName,

      @NotNull
      LocalDateTime dob,

      @NotNull
      @NotBlank
      @Email
      String email,

      @NotNull
      @NotBlank
      String phone,

      @NotNull
      @NotBlank
      String address,

      @NotNull
      EmployeeStatus status
) implements Serializable {
}