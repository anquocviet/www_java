package fit.se.week07_AnQuocViet_21080821.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 12/10/24
 */
@Data
@RequiredArgsConstructor
public class Skill {
   private final int id;
   private final String skillName;
   private final String description;
   private final String field;
   private Set<SkillCandidate> skillCandidates;
   private Set<JobSkill> jobSkills;

   // Constructor for creating a new skill
   public Skill(String skillName, String description, String field) {
      this .id = -1;
      this.skillName = skillName;
      this.description = description;
      this.field = field;
   }
}
