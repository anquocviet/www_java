package fit.se.backend.mappers;

import fit.se.backend.dtos.SkillDto;
import fit.se.backend.models.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
   SkillDto toSkillDto(Skill skill);

   Skill toSkill(SkillDto skillDto);
}
