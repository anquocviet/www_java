package fit.se.backend.dtos;

import fit.se.backend.enums.SkillLevel;

public record SkillWithLevelDTO(
      Long skillId,
      SkillLevel skillLevel
) {
}
