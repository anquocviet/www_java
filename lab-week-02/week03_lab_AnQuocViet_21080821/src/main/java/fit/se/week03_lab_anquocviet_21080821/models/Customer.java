package fit.se.week03_lab_anquocviet_21080821.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
@NamedQueries({
      @NamedQuery(name = "Customer.findAll", query = "select c from Customer c")
})
public class Customer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "cust_id")
   private long id;

   @Column(name = "cust_name")
   private String name;

   private String email;

   private String phone;

   private String address;

   @OneToMany(mappedBy = "customer")
   @JsonIgnore
   private Set<Order> orders;
}