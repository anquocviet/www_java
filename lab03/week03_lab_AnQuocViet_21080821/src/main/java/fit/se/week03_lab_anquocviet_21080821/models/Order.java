package fit.se.week03_lab_anquocviet_21080821.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "order_id")
   private long id;

   @Column(name = "order_date")
   private LocalDateTime orderDate;

   @ManyToOne
   @JoinColumn(name = "emp_id")
   private Employee employee;

   @ManyToOne
   @JoinColumn(name = "cust_id")
   private Customer customer;

   @OneToMany(mappedBy = "order")
   private Set<OrderDetail> orderDetails;


}