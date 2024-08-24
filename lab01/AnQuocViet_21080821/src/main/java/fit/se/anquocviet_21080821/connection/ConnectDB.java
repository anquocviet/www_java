package fit.se.anquocviet_21080821.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description
 * @author: vie
 * @date: 24/8/24
 */
public class ConnectDB {
   private static Connection instance;

   public static void instanceConnection(String user, String password) {
      if (instance == null) {
         try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://localhost:3306/www_lab01?user=" + user + "&password=" + password;
            instance = DriverManager.getConnection(url);
         } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public static Connection getInstance() {
      return instance;
   }
}
