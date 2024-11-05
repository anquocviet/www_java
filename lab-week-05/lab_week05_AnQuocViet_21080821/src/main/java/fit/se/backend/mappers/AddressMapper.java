package fit.se.backend.mappers;

import fit.se.backend.dtos.AddressDto;
import fit.se.backend.models.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {
   Address toEntity(AddressDto addressDto);

   AddressDto toDto(Address address);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   Address partialUpdate(AddressDto addressDto, @MappingTarget Address address);
}
