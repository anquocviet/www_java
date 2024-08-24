package fit.se.anquocviet_21080821.beans;

import fit.se.anquocviet_21080821.connection.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @description
 * @author: vie
 * @date: 24/8/24
 */
public class AuthBean {
   private final Connection connection;

   public AuthBean() {
      ConnectDB.instanceConnection("root", "anquocviet_203");
      connection = ConnectDB.getInstance();
   }

   public boolean login(String username, String password) {
      String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql);) {
         statement.setString(1, username);
         statement.setString(2, password);
         ResultSet resultSet = statement.executeQuery();

         while (resultSet.next()) {
            String resultUsername = resultSet.getString(1);
            String resultPass = resultSet.getString(2);

            if (resultUsername == null || resultPass == null) {
               return false;
            }
            if (resultUsername.isEmpty() || resultPass.isEmpty()) {
               return false;
            }
            if (resultUsername.equals(username) && resultPass.equals(password)) {
               return true;
            }
         }

      } catch (Exception e) {
         throw new RuntimeException(e);
      }
      return false;
   }
}
