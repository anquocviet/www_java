package fit.se.backend.dtos;

import fit.se.backend.enums.SkillLevel;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.backend.models.JobSkill}
 */
public record JobSkillDto(
      SkillDto skill,
      String moreInfos,
      SkillLevel skillLevel
) implements Serializable {
}