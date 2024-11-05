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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "job_id", nullable = false)
   private Long id;

   @Column(name = "job_desc", nullable = false, length = 2000)
   private String jobDesc;

   @Column(name = "job_name", nullable = false)
   private String jobName;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "company")
   private Company company;

   @OneToMany(mappedBy = "job")
   private Set<JobSkill> jobSkills = new LinkedHashSet<>();

}