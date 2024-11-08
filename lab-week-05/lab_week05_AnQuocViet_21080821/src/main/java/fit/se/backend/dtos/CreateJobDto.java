package fit.se.backend.dtos;

import fit.se.backend.models.Job;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link Job}
 */
public record CreateJobDto(
      @NotEmpty(message = "Job description is required")
      String jobDesc,

      @NotEmpty(message = "Job name is required")
      String jobName,

      @NotNull(message = "Company is required")
      Long company,

      @NotNull(message = "Job skills is not null")
      @NotEmpty(message = "Job skills is not empty")
      List<CreateJobSkillDto> jobSkills
) implements Serializable {
   public CreateJobDto {
      // Ensure jobSkills is not null, spring not automatically create an empty list
      jobSkills = Objects.requireNonNullElseGet(jobSkills, LinkedList::new);
   }
}


