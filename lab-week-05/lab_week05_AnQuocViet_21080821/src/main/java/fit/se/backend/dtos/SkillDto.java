package fit.se.backend.dtos;

import fit.se.backend.enums.SkillType;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.backend.models.Skill}
 */
public record SkillDto(
      Long id,
      String skillDescription,
      String skillName,
      SkillType type
) implements Serializable {
}