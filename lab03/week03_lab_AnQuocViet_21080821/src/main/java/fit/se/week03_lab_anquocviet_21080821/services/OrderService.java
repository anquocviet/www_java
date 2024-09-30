package fit.se.week03_lab_anquocviet_21080821.services;

import fit.se.week03_lab_anquocviet_21080821.converters.OrderConverter;
import fit.se.week03_lab_anquocviet_21080821.dtos.CreateOrderDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.CustomerDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.EmployeeDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.OrderDetailDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.OrderDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.repositories.OrderRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */
@Stateless
public class OrderService {
   @Inject
   private OrderRepository orderRepository;
   @Inject
   private CustomerService customerService;
   @Inject
   private EmployeeService employeeService;
   @Inject
   private ProductService productService;

   public Set<OrderDto> getAllOrders() {
      return orderRepository
                   .findAll().stream()
                   .map(OrderConverter::convertToDto)
                   .collect(Collectors.toSet());
   }

   public OrderDto getOrderById(long id) {
      return orderRepository
                   .findById(id)
                   .map(OrderConverter::convertToDto)
                   .orElseThrow(() -> new RuntimeException("Order not found"));
   }

   public OrderDto createOrder(CreateOrderDto dto) {
      if (dto == null) {
         throw new IllegalArgumentException("Order cannot be null");
      }
      CustomerDto customer = customerService.getCustomerById(dto.customerId());
      if (customer == null) {
         throw new IllegalArgumentException("Customer not found");
      }
      EmployeeDto employee = employeeService.getEmployeeById(dto.employeeId());
      if (employee == null) {
         throw new IllegalArgumentException("Employee not found");
      }
      Set<OrderDetailDto> orderDetailDtos =
            dto.orderDetails()
                  .stream()
                  .map(od -> {
                           ProductDto product = productService.getProductById(od.productId());
                           return new OrderDetailDto(
                                 product.price().price(),
                                 od.quantity(),
                                 od.note(),
                                 product
                           );
                        }
                  )
                  .collect(Collectors.toSet());
      OrderDto orderDto = new OrderDto(
            0,
            dto.orderDate(),
            employee,
            customer,
            orderDetailDtos
      );

      orderRepository.create(OrderConverter.convertToModel(orderDto));
      return orderDto;
   }

   public OrderDto updateOrder(OrderDto orderDto) {
      if (orderDto == null) {
         throw new IllegalArgumentException("Order is null");
      }
      orderRepository.findById(orderDto.id()).orElseThrow(
            () -> new EntityNotFoundException("Order not found"));
      orderRepository.update(OrderConverter.convertToModel(orderDto));
      return orderDto;
   }

   public void deleteOrder(int id) {
      boolean delete = orderRepository.delete(id);
      if (!delete) {
         throw new EntityNotFoundException("Order not found");
      }
   }

   public String statisticsByDate(String date) {
      LocalDate localDate = LocalDate.parse(date);
      return orderRepository.statisticsByDate(localDate)
                   .entrySet()
                   .stream()
                   .map(e -> String.format("Date: %s, Quantity of orders: %d, Total amount: %f",
                         date, e.getKey(), e.getValue()))
                   .reduce("", (a, b) -> a + "\n" + b);
   }

   public String statisticsBetweenDates(String fromDate, String toDate) {
      LocalDate localFromDate = LocalDate.parse(fromDate);
      LocalDate localToDate = LocalDate.parse(toDate);
      return orderRepository.statisticsBetweenDates(localFromDate, localToDate)
                   .entrySet()
                   .stream()
                   .map(e -> String.format("Date: %s - %s, Quantity of orders: %d, Total amount: %f",
                         fromDate, toDate, e.getKey(), e.getValue()))
                   .reduce("", (a, b) -> a + "\n" + b);
   }

   public String statisticsByEmployeeBetweenDates(long employeeId, String fromDate, String toDate) {
      LocalDate localFromDate = LocalDate.parse(fromDate);
      LocalDate localToDate = LocalDate.parse(toDate);
      return orderRepository.statisticsByEmployeeBetweenDates(employeeId, localFromDate, localToDate)
                   .entrySet()
                   .stream()
                   .map(e -> String.format("""
                               Employee: %s
                               Date: %s - %s
                               Quantity of orders: %d
                               Total amount: %f""",
                         employeeId, fromDate, toDate, e.getKey(), e.getValue()))
                   .reduce("", (a, b) -> a + "\n" + b);
   }
}
