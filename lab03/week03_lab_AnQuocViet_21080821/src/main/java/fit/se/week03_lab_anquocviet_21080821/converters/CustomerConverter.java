package fit.se.week03_lab_anquocviet_21080821.converters;

import fit.se.week03_lab_anquocviet_21080821.dtos.CustomerDto;
import fit.se.week03_lab_anquocviet_21080821.models.Customer;

/**
 * @description
 * @author: vie
 * @date: 30/9/24
 */
public class CustomerConverter {
   private CustomerConverter() {
   }

   public static CustomerDto convertToDto(Customer customer) {
      return new CustomerDto(
            customer.getId(),
            customer.getName(),
            customer.getPhone(),
            customer.getEmail(),
            customer.getAddress()
      );
   }

   public static Customer convertToModel(CustomerDto customerDto) {
      Customer customer = new Customer();
      customer.setId(customerDto.id());
      customer.setName(customerDto.name());
      customer.setPhone(customerDto.phone());
      customer.setEmail(customerDto.email());
      customer.setAddress(customerDto.address());
      return customer;
   }
}
