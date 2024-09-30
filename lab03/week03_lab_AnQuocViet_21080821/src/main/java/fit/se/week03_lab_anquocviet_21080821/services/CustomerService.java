package fit.se.week03_lab_anquocviet_21080821.services;

import fit.se.week03_lab_anquocviet_21080821.converters.CustomerConverter;
import fit.se.week03_lab_anquocviet_21080821.dtos.CustomerDto;
import fit.se.week03_lab_anquocviet_21080821.repositories.CustomerRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */

@Stateless
public class CustomerService {
   @Inject
   private CustomerRepository customerRepository;

   public Set<CustomerDto> getAllCustomers() {
      return customerRepository
                   .findAll().stream()
                   .map(CustomerConverter::convertToDto)
                   .collect(Collectors.toSet());
   }

   public CustomerDto getCustomerById(long id) {
      return customerRepository
                   .findById(id)
                   .map(CustomerConverter::convertToDto)
                   .orElseThrow(() -> new RuntimeException("Customer not found"));
   }

   public CustomerDto createCustomer(CustomerDto customerDto) {
      if (customerDto == null) {
         throw new IllegalArgumentException("Customer cannot be null");
      }
      customerRepository.findById(customerDto.id()).ifPresent(c -> {
         throw new IllegalStateException("Customer already exists");
      });
      customerRepository.create(CustomerConverter.convertToModel(customerDto));
      return customerDto;
   }

   public CustomerDto updateCustomer(CustomerDto customerDto) {
      if (customerDto == null) {
         throw new IllegalArgumentException("Customer cannot be null");
      }
      customerRepository.findById(customerDto.id()).orElseThrow(
            () -> new EntityNotFoundException("Customer not found"));
      customerRepository.update(CustomerConverter.convertToModel(customerDto));
      return customerDto;
   }

   public void deleteCustomer(int id) {
      boolean delete = customerRepository.delete(id);
      if (!delete) {
         throw new EntityNotFoundException("Customer not found");
      }
   }
}
