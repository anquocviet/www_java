package fit.se.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
public class CypherService {
   private final PasswordEncoder passwordEncoder;

   public CypherService() {
      this.passwordEncoder = new BCryptPasswordEncoder();
   }

   public String encryptPassword(String rawPassword) {
      return passwordEncoder.encode(rawPassword);
   }

   public boolean verifyPassword(String rawPassword, String encodedPassword) {
      return passwordEncoder.matches(rawPassword, encodedPassword);
   }
}
