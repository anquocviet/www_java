package fit.se.week03_lab_anquocviet_21080821.converters;

import fit.se.week03_lab_anquocviet_21080821.dtos.OrderDetailDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.OrderDto;
import fit.se.week03_lab_anquocviet_21080821.models.Order;
import fit.se.week03_lab_anquocviet_21080821.models.OrderDetail;

import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 30/9/24
 */
public class OrderConverter {
   public static OrderDto convertToDto(Order order) {
      return new OrderDto(
            order.getId(),
            order.getOrderDate(),
            EmployeeConverter.convertToDto(order.getEmployee()),
            CustomerConverter.convertToDto(order.getCustomer()),
            order.getOrderDetails().stream()
                  .map(od -> new OrderDetailDto(
                        od.getPrice(),
                        od.getQuantity(),
                        od.getNote(),
                        ProductConverter.convertToDto(od.getProduct())
                  ))
                  .collect(Collectors.toSet())
      );
   }

   public static Order convertToModel(OrderDto orderDto) {
      Order order = new Order();
      order.setId(orderDto.id());
      order.setOrderDate(orderDto.orderDate());
      order.setEmployee(EmployeeConverter.convertToModel(orderDto.employee()));
      order.setCustomer(CustomerConverter.convertToModel(orderDto.customer()));
      order.setOrderDetails(orderDto.orderDetails().stream()
                                  .map(od -> {
                                     OrderDetail orderDetail = new OrderDetail();
                                     orderDetail.setPrice(od.price());
                                     orderDetail.setQuantity(od.quantity());
                                     orderDetail.setNote(od.note());
                                     orderDetail.setProduct(ProductConverter.convertToModel(od.product()));
                                     return orderDetail;
                                  })
                                  .collect(Collectors.toSet())
      );
      return order;
   }
}
