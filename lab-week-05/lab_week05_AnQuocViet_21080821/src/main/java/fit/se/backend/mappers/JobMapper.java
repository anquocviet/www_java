package fit.se.backend.mappers;

import fit.se.backend.dtos.JobDto;
import fit.se.backend.models.Job;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface JobMapper {
   @Mapping(target = "companyId", source = "company.id")
   @Mapping(target = "companyName", source = "company.compName")
   JobDto toDto(Job job);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   Job partialUpdate(JobDto jobDto, @MappingTarget Job job);
}
