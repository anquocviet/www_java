package fit.se.backend.mappers;

import fit.se.backend.dtos.CandidateSkillDto;
import fit.se.backend.models.CandidateSkill;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CandidateSkillMapper {
   CandidateSkill toEntity(CandidateSkillDto candidateSkillDto);

   CandidateSkillDto toDto(CandidateSkill candidateSkill);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   CandidateSkill partialUpdate(CandidateSkillDto candidateSkillDto, @MappingTarget CandidateSkill candidateSkill);
}
