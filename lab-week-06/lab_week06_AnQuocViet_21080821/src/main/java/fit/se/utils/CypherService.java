package fit.se.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
public class CypherService {
   public static String encryptPassword(String password) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(password.getBytes());
         StringBuilder hexString = new StringBuilder(64);
         for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
         }
         // Truncate to 32 characters if necessary
         return hexString.substring(0, 32);
      } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException("Error encrypting password", e);
      }
   }

   public static boolean verifyPassword(String rawPassword, String encodedPassword) {
      String encryptedPassword = encryptPassword(rawPassword);
      return encryptedPassword.equals(encodedPassword);
   }
}
