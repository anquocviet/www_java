package fit.se.week03_lab_anquocviet_21080821.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link fit.se.week03_lab_anquocviet_21080821.models.Order}
 */
public record OrderDto(
      @NotNull
      long id,

      @NotNull
      LocalDateTime orderDate,

      EmployeeDto employee,

      CustomerDto customer,
      
      Set<OrderDetailDto> orderDetails
) implements Serializable {
}