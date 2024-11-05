package fit.se.week03_lab_anquocviet_21080821.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for creating an order.
 * This class is used to transfer order data between processes.
 *
 * @param id The ID of the order. Must not be null.
 * @param orderDate The date and time when the order was placed. Must not be null.
 * @param employeeId The ID of the employee who created the order. Must not be null or blank.
 * @param customerId The ID of the customer who placed the order. Must not be null or blank.
 * @param orderDetails A set of order details, each containing information about a product in the order. Must not be null.
 *
 * @author vie
 * @date 19/9/24
 */
public record CreateOrderDto(
      @NotNull
      LocalDateTime orderDate,

      @NotNull
      long employeeId,

      @NotNull
      long customerId,

      @NotNull
      @NotEmpty
      Set<CreateOrderDetailDto> orderDetails
) {
   /**
    * Data Transfer Object (DTO) for creating order details.
    * This class is used to transfer order detail data between processes.
    *
    * @param quantity The quantity of the product ordered. Must not be null.
    * @param note Additional notes about the order detail. Must not be null or blank.
    * @param productId The ID of the product. Must not be null or blank.
    */
   public record CreateOrderDetailDto(
         @NotNull
         double quantity,

         @NotNull
         String note,

         @NotNull
         long productId
   ) {
   }
}