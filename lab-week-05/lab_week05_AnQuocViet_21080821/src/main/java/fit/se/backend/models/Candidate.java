package fit.se.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "candidate")
public class Candidate {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "dob", nullable = false)
   private LocalDate dob;

   @Column(name = "email", nullable = false)
   private String email;

   @Column(name = "full_name", nullable = false)
   private String fullName;

   @Column(name = "phone", nullable = false, length = 15)
   private String phone;

   @OneToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "address", nullable = false)
   private Address address;

   @OneToMany(mappedBy = "can")
   private Set<CandidateSkill> candidateSkills = new LinkedHashSet<>();

   @OneToMany(mappedBy = "candidate")
   private Set<Experience> experiences = new LinkedHashSet<>();

}