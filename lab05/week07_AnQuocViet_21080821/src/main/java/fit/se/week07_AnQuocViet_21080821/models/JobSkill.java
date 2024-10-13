package fit.se.week07_AnQuocViet_21080821.models;

import lombok.Data;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
@Data
public class JobSkill {
   private final Job job;
   private final Skill skill;
   private int level;
}
