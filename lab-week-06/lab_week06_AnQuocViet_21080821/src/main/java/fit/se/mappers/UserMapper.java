package fit.se.mappers;

import fit.se.dtos.UserDto;
import fit.se.dtos.RegisterUserDto;
import fit.se.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
   User toEntity(UserDto userDto);

   UserDto toDto(User user);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   User partialUpdate(UserDto userDto, @MappingTarget User user);

   @Mapping(target = "passwordHash", source = "password")
   @Mapping(target = "registeredAt", expression = "java(java.time.Instant.now())")
   User toEntity(RegisterUserDto registerUserDto);

   RegisterUserDto toRegisterDto(User user);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   User partialUpdate(RegisterUserDto registerUserDto, @MappingTarget User user);
}
