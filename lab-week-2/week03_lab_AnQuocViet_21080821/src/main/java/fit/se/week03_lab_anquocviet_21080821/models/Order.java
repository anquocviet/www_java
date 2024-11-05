package fit.se.week03_lab_anquocviet_21080821.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "orders")
@NamedQueries({
      @NamedQuery(name = "Order.findAll", query = "select o from Order o"),
      @NamedQuery(name = "Order.statisticsByDay",
            query = "select count(o) as orderCount, od.price * od.quantity as totalAmount" +
                          " from Order o inner join o.orderDetails od" +
                          " where function('date', o.orderDate) = :date" +
                          " group by function('date', o.orderDate)"),
      @NamedQuery(name = "Order.statisticsBetweenDates",
            query = "select count(o) as orderCount, sum(od.price * od.quantity) as totalAmount" +
                          " from Order o inner join o.orderDetails od" +
                          " where function('date', o.orderDate) between :startDate and :endDate"),
      @NamedQuery(name = "Order.statisticsByEmployeeBetweenDates",
            query = "select count(o) as orderCount, sum(od.price * od.quantity) as totalAmount" +
                          " from Order o inner join o.orderDetails od" +
                          " where o.employee.id = :empId and function('date', o.orderDate) between :startDate and :endDate")
})
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
   @JsonManagedReference
   private Set<OrderDetail> orderDetails;


}