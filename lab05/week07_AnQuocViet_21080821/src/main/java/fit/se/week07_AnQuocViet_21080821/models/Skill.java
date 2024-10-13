package fit.se.week07_AnQuocViet_21080821.models;

import lombok.Data;

import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 12/10/24
 */
@Data
public class Skill {
   private final int id;
   private final String skillName;
   private final String description;
   private final String field;
   private final Set<SkillCandidate> skillCandidates;
   private Set<JobSkill> jobSkills;
}
