package fit.se.week03_lab_anquocviet_21080821.services;

import fit.se.week03_lab_anquocviet_21080821.converters.ModelDtoConverter;
import fit.se.week03_lab_anquocviet_21080821.dtos.CustomerDto;
import fit.se.week03_lab_anquocviet_21080821.models.Customer;
import fit.se.week03_lab_anquocviet_21080821.repositories.CustomerRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */
public class CustomerService {
   @Inject
   private CustomerRepository customerRepository;

   public Set<CustomerDto> getAllCustomers() {
      return customerRepository
                   .findAll().stream()
                   .map(c -> ModelDtoConverter.convertToDto(c, CustomerDto.class))
                   .collect(Collectors.toSet());
   }

   public CustomerDto getCustomerById(long id) {
      return customerRepository
                   .findById(id)
                   .map(c -> ModelDtoConverter.convertToDto(c, CustomerDto.class))
                   .orElseThrow(() -> new RuntimeException("Customer not found"));
   }

   public CustomerDto createCustomer(CustomerDto customerDto) {
      if (customerDto == null) {
         throw new IllegalArgumentException("Customer cannot be null");
      }
      customerRepository.findById(customerDto.id()).ifPresent(c -> {
         throw new IllegalStateException("Customer already exists");
      });
      customerRepository.create(ModelDtoConverter.convertToModel(customerDto, Customer.class));
      return customerDto;
   }

   public CustomerDto updateCustomer(CustomerDto customerDto) {
      if (customerDto == null) {
         throw new IllegalArgumentException("Customer cannot be null");
      }
      customerRepository.findById(customerDto.id()).orElseThrow(
            () -> new EntityNotFoundException("Customer not found"));
      customerRepository.update(ModelDtoConverter.convertToModel(customerDto, Customer.class));
      return customerDto;
   }

   public void deleteCustomer(int id) {
      boolean delete = customerRepository.delete(id);
      if (!delete) {
         throw new EntityNotFoundException("Customer not found");
      }
   }
}
