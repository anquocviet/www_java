package fit.se.mappers;

import fit.se.dtos.UserDto;
import fit.se.entities.RegisterUserDto;
import fit.se.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
   User toEntity(UserDto userDto);

   UserDto toDto(User user);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   User partialUpdate(UserDto userDto, @MappingTarget User user);

   User toEntity(RegisterUserDto registerUserDto);

   RegisterUserDto toRegisterDto(User user);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   User partialUpdate(RegisterUserDto registerUserDto, @MappingTarget User user);
}
