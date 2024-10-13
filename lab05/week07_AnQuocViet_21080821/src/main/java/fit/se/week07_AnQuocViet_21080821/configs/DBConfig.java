package fit.se.week07_AnQuocViet_21080821.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @description
 * @author: vie
 * @date: 12/10/24
 */
@Configuration
public class DBConfig {
   @Bean
   @Scope("singleton")
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
      dataSource.setUrl("jdbc:mariadb://localhost:3306/lab05?createDatabaseIfNotExist=true");
      dataSource.setUsername("root");
      dataSource.setPassword("anquocviet_203");
      return dataSource;
   }
}
