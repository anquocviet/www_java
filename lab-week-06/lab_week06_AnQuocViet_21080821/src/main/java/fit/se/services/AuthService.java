package fit.se.services;

import fit.se.dtos.LoginDto;
import fit.se.dtos.UserDto;
import fit.se.dtos.RegisterUserDto;
import fit.se.entities.User;
import fit.se.exceptions.AppException;
import fit.se.mappers.UserMapper;
import fit.se.repositories.UserRepository;
import fit.se.utils.CypherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@Service
public class AuthService {
   private final UserRepository userRepository;
   private final UserMapper userMapper;
   private final Logger logger = Logger.getLogger(AuthService.class.getName());

   public AuthService(UserRepository userRepository,
                      UserMapper userMapper) {
      this.userRepository = userRepository;
      this.userMapper = userMapper;
   }

   public boolean register(RegisterUserDto registerUserDto) {
      if (userRepository.existsByEmail(registerUserDto.email())) {
         throw new AppException(400, "Email already exists");
      }
      if (userRepository.existsByMobile(registerUserDto.mobile())) {
         throw new AppException(400, "Mobile already exists");
      }
      User entity = userMapper.toEntity(registerUserDto);
      String passwordHash = CypherService.encryptPassword(entity.getPasswordHash());
      logger.info("Password hash: " + passwordHash + " size: " + passwordHash.length());
      entity.setPasswordHash(passwordHash);
      userRepository.save(entity);
      return true;
   }

   public UserDto login(@Valid LoginDto loginDto) {
      Optional<User> userOpt = userRepository.findByEmail(loginDto.email());
      if (userOpt.isEmpty()) {
         throw new AppException(400, "User not found");
      }
      User user = userOpt.get();
      if (CypherService.verifyPassword(loginDto.password(), user.getPasswordHash())) {
         userRepository.updateLastLoginById(Instant.now(), user.getId());
         return userMapper.toDto(user);
      }
      throw new AppException(400, "Login failed");
   }
}
