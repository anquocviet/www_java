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
public class Job {
   private final int id;
   private final String description;
   private Set<JobSkill> jobSkills;

   public Job(String description) {
      this.id = -1;
      this.description = description;
   }
}
