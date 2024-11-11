package fit.se.backend.dtos;

import fit.se.backend.enums.SkillLevel;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.backend.models.CandidateSkill}
 */
public record CandidateSkillDto(
      String moreInfos,
      SkillLevel skillLevel,
      SkillDto skill
) implements Serializable {
}