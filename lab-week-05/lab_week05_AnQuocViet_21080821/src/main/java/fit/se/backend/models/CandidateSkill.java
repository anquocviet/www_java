package fit.se.backend.models;

import fit.se.backend.enums.SkillLevel;
import fit.se.backend.ids.CandidateSkillId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "candidate_skill")
public class CandidateSkill {
   @EmbeddedId
   private CandidateSkillId id;

   @MapsId("canId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "can_id", nullable = false)
   private Candidate can;

   @MapsId("skillId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "skill_id", nullable = false)
   private Skill skill;

   @Column(name = "more_infos", length = 1000)
   private String moreInfos;

   @Column(name = "skill_level", nullable = false)
   @Enumerated(EnumType.ORDINAL)
   private SkillLevel skillLevel;

}