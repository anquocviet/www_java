package fit.se.week07_AnQuocViet_21080821.models;

import lombok.Data;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
@Data
public class SkillCandidate {
   private final Candidate candidate;
   private final Skill skill;
   private int level;
}
