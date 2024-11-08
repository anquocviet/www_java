package fit.se.backend.dtos;

import fit.se.backend.enums.SkillLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobSkillDto implements Serializable {
   @Serial
   private static final long serialVersionUID = 0L;
   private @NotNull(message = "Skill ID is required") Long skillId;
   private @NotNull(message = "Skill level is required") SkillLevel skillLevel;
   private String moreInfos;

}
