package fit.se.week03_lab_anquocviet_21080821.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product_prices")
public class ProductPrice {
   @Id
   @Column(name = "price_date_time")
   private LocalDateTime priceDateTime;

   @ManyToOne
   @JoinColumn(name = "product_id")
   @JsonBackReference
   private Product product;

   private double price;

   private String note;
}