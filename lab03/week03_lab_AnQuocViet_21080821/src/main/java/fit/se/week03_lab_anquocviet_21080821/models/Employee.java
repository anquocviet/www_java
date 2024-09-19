package fit.se.week03_lab_anquocviet_21080821.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fit.se.week03_lab_anquocviet_21080821.enums.EmployeeStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employees")
@NamedQueries({
      @NamedQuery(name = "Employee.findAll", query = "select e from Employee e")
})
public class Employee {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "emp_id")
   private long id;

   @Column(name = "full_name")
   private String fullName;

   private LocalDateTime dob;

   private String email;

   private String phone;

   private String address;

   @Enumerated(EnumType.ORDINAL)
   private EmployeeStatus status;

   @OneToMany(mappedBy = "employee")
   @JsonIgnore
   private Set<Order> orders;
}