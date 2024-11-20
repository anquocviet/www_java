package fit.se.backend.mappers;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.CreateCandidateDTO;
import fit.se.backend.models.Candidate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CandidateMapper {
   Candidate toEntity(CandidateDto candidateDto);

   @Mapping(target = "skills", source = "candidateSkills")
   CandidateDto toDto(Candidate candidate);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   Candidate partialUpdate(CandidateDto candidateDto, @MappingTarget Candidate candidate);

   Candidate toEntity(CreateCandidateDTO createCandidateDTO);

   CreateCandidateDTO toDto1(Candidate candidate);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   Candidate partialUpdate(CreateCandidateDTO createCandidateDTO, @MappingTarget Candidate candidate);
}
