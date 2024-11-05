package fit.se.backend.models;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "street", length = 150)
   private String street;

   @Column(name = "city", length = 50)
   private String city;

   @Column(name = "country")
   private CountryCode country;

   @Column(name = "number", length = 20)
   private String number;

   @Column(name = "zipcode", length = 7)
   private String zipcode;

   @OneToOne(mappedBy = "address")
   private Candidate candidate;

   @OneToOne(mappedBy = "address")
   private Company company;

}