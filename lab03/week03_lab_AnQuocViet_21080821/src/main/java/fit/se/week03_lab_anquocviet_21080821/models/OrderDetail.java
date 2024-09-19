package fit.se.week03_lab_anquocviet_21080821.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_details")
public class OrderDetail {
   @Id
   @ManyToOne
   @JoinColumn(name = "order_id")
   @JsonBackReference
   private Order order;

   @Id
   @ManyToOne
   @JoinColumn(name = "product_id")
   private Product product;

   private double price;

   private double quantity;

   private String note;
}