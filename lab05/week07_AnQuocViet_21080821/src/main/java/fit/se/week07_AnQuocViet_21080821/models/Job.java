package fit.se.week07_AnQuocViet_21080821.models;

import lombok.Data;

import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 12/10/24
 */
@Data
public class Job {
   private final int id;
   private final String description;
   private Set<JobSkill> jobSkills;
}
