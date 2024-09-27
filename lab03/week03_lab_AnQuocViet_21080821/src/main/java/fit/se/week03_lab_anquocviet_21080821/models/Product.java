package fit.se.week03_lab_anquocviet_21080821.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fit.se.week03_lab_anquocviet_21080821.enums.ProductStatus;
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
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
@NamedQueries({
      @NamedQuery(name = "Product.findAll", query = "select p from Product p where p.status = :status"),
      @NamedQuery(name = "Product.findByIds", query = "select p from Product p where p.id in :ids")
})
public class Product implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "product_id")
   private long id;

   private String name;

   private String description;

   private String unit;

   @Column(name = "manufacturer_name")
   private String manufacturer;

   @Enumerated(EnumType.ORDINAL)
   private ProductStatus status;

   @OneToMany(mappedBy = "product")
   @ToString.Exclude
   @JsonManagedReference
   private Set<ProductPrice> prices;

   @OneToMany(mappedBy = "product")
   @ToString.Exclude
   @JsonManagedReference
   private Set<ProductImage> images;

   @OneToMany(mappedBy = "product")
   @ToString.Exclude
   @JsonIgnore
   private Set<OrderDetail> orderDetails;
}