package fit.se.week03_lab_anquocviet_21080821.services;

import fit.se.week03_lab_anquocviet_21080821.converters.EmployeeConverter;
import fit.se.week03_lab_anquocviet_21080821.dtos.EmployeeDto;
import fit.se.week03_lab_anquocviet_21080821.repositories.EmployeeRepository;
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
public class EmployeeService {
   @Inject
   private EmployeeRepository employeeRepository;

   public Set<EmployeeDto> getAllEmployees() {
      return employeeRepository
                   .findAll().stream()
                   .map(EmployeeConverter::convertToDto)
                   .collect(Collectors.toSet());
   }

   public EmployeeDto getEmployeeById(long id) {
      return employeeRepository
                   .findById(id)
                   .map(EmployeeConverter::convertToDto)
                   .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
   }

   public EmployeeDto createEmployee(EmployeeDto employeeDto) {
      if (employeeDto == null) {
         throw new IllegalArgumentException("Employee cannot be null");
      }
      employeeRepository.findById(employeeDto.id()).ifPresent(e -> {
         throw new IllegalStateException("Employee already exists");
      });
      employeeRepository.create(EmployeeConverter.convertToModel(employeeDto));
      return employeeDto;
   }

   public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
      if (employeeDto == null) {
         throw new IllegalArgumentException("Employee cannot be null");
      }
      employeeRepository.findById(employeeDto.id()).orElseThrow(
            () -> new EntityNotFoundException("Employee not found"));
      employeeRepository.update(EmployeeConverter.convertToModel(employeeDto));
      return employeeDto;
   }

   public void deleteEmployee(int id) {
      boolean delete = employeeRepository.delete(id);
      if (!delete) {
         throw new EntityNotFoundException("Employee not found");
      }
   }
}
