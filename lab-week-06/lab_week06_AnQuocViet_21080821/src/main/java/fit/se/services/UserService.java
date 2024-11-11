package fit.se.services;

import fit.se.dtos.UserDto;
import fit.se.entities.User;
import fit.se.exceptions.AppException;
import fit.se.mappers.UserMapper;
import fit.se.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@Service
public class UserService {
   private final UserRepository userRepository;
   private final UserMapper userMapper;

   public UserService(UserRepository userRepository,
                      UserMapper userMapper) {
      this.userRepository = userRepository;
      this.userMapper = userMapper;
   }

   public UserDto update(UserDto userDto) {
      User user = userMapper.toEntity(userDto);
      if (userRepository.existsById(user.getId())) {
         user = userRepository.save(user);
         return userMapper.toDto(user);
      }
      throw new AppException(404, "User not found");
   }

   public boolean delete(Long id) {
      if (userRepository.existsById(id)) {
         userRepository.deleteById(id);
         return true;
      }
      throw new AppException(404, "User not found");
   }

   public UserDto findById(Long id) {
      User user = userRepository.findById(id)
                        .orElseThrow(() -> new AppException(404, "User not found"));
      return userMapper.toDto(user);
   }

   public List<UserDto> findAll() {
      return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                   .map(userMapper::toDto)
                   .toList();
   }
}
