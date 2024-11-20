package fit.se.backend.configs;

import fit.se.backend.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
   private final CustomUserDetailsService userService;

   public SecurityConfig(CustomUserDetailsService userService) {
      this.userService = userService;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                  .requestMatchers("/", "/jobs", "/jobs/").permitAll()
                  .requestMatchers("/auth/**").permitAll()
                  // authorize requests based on the role
                  .requestMatchers("/candidates/edit/**", "/companies/").hasRole("CANDIDATE")
                  .requestMatchers("/companies/edit/**", "/candidates/").hasRole("COMPANY")
                  .anyRequest().authenticated()
            )
            .userDetailsService(userService)
            .formLogin(form -> form
                  .loginPage("/auth/login")
                  .defaultSuccessUrl("/") // redirect to home page after login
                  .permitAll()
            );
      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
}
