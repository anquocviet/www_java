package fit.se.backend.mappers;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.models.Candidate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
   Candidate toEntity(CandidateDto candidateDto);

   CandidateDto toDto(Candidate candidate);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   Candidate partialUpdate(CandidateDto candidateDto, @MappingTarget Candidate candidate);
}
