package fit.se.week03_lab_anquocviet_21080821.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(ProductPriceId.class)
public class ProductPrice {
   @Id
   @Column(name = "price_date_time")
   private LocalDateTime priceDateTime;

   @Id
   @ManyToOne
   @JoinColumn(name = "product_id")
   private Product product;

   private double price;

   private String note;
}