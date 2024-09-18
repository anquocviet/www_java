package fit.se.week03_lab_anquocviet_21080821.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_image")
public class ProductImage {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "image_id")
   private long id;

   @Id
   @ManyToOne
   @JoinColumn(name = "product_id")
   private Product product;

   private String path;

   private String alternative;
}