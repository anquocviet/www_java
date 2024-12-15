package fit.se.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "experience")
public class Experience {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "exp_id", nullable = false)
   private Long id;

   @Column(name = "company_name", nullable = false)
   private String companyName;

   @Column(name = "from_date", nullable = false)
   private LocalDate fromDate;

   @Column(name = "to_date", nullable = false)
   private LocalDate toDate;

   private String role;

   @Column(name = "work_desc", nullable = false)
   private String workDescription;

   @ManyToOne
   @JoinColumn(name = "can_id", nullable = false)
   private Candidate candidate;
}