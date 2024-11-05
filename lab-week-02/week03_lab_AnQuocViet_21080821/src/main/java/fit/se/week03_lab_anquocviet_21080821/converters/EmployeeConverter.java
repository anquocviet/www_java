package fit.se.week03_lab_anquocviet_21080821.converters;

import fit.se.week03_lab_anquocviet_21080821.dtos.EmployeeDto;
import fit.se.week03_lab_anquocviet_21080821.models.Employee;

/**
 * @description
 * @author: vie
 * @date: 30/9/24
 */
public class EmployeeConverter {
   private EmployeeConverter() {
   }

   public static EmployeeDto convertToDto(Employee e) {
      return new EmployeeDto(
            e.getId(),
            e.getFullName(),
            e.getDob(),
            e.getEmail(),
            e.getPhone(),
            e.getAddress(),
            e.getStatus()
      );
   }

   public static Employee convertToModel(EmployeeDto e) {
      Employee employee = new Employee();
      employee.setId(e.id());
      employee.setFullName(e.fullName());
      employee.setDob(e.dob());
      employee.setEmail(e.email());
      employee.setPhone(e.phone());
      employee.setAddress(e.address());
      employee.setStatus(e.status());
      return employee;
   }
}
