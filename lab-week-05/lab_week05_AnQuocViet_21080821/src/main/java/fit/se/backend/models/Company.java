package fit.se.backend.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "comp_id", nullable = false)
   private Long id;

   @Column(name = "about", length = 2000)
   private String about;

   @Column(name = "email", nullable = false)
   private String email;

   @Column(nullable = false)
   private String password;

   @Column(name = "comp_name", nullable = false)
   private String compName;

   @Column(name = "phone", nullable = false)
   private String phone;

   @Column(name = "web_url")
   private String webUrl;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "address", nullable = false)
   private Address address;

   @OneToMany(mappedBy = "company")
   private Set<Job> jobs = new LinkedHashSet<>();

}