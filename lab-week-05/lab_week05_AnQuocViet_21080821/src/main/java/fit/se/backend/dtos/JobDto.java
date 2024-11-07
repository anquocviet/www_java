package fit.se.backend.dtos;

import fit.se.backend.models.Job;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Job}
 */
public record JobDto(
      Long id,
      String jobDesc,
      String jobName,
      Long companyId,
      String companyName,
      String companyEmail,
      @NotNull
      Set<JobSkillDto> jobSkills
) implements Serializable {
}