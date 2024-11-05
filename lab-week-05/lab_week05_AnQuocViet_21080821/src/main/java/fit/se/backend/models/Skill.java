package fit.se.backend.models;

import fit.se.backend.enums.SkillType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "skill")
public class Skill {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "skill_id", nullable = false)
   private Long id;

   @Column(name = "skill_description")
   private String skillDescription;

   @Column(name = "skill_name")
   private String skillName;

   @Column(name = "type")
   @Enumerated(EnumType.ORDINAL)
   private SkillType type;

   @OneToMany(mappedBy = "skill")
   private Set<CandidateSkill> candidateSkills = new LinkedHashSet<>();

   @OneToMany(mappedBy = "skill")
   private Set<JobSkill> jobSkills = new LinkedHashSet<>();

}