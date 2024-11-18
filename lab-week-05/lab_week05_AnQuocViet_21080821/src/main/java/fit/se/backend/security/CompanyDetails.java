package fit.se.backend.security;

import fit.se.backend.models.Company;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
public class CompanyDetails implements UserDetails {
   private final Company company;

   public CompanyDetails(Company company) {
      this.company = company;
   }


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.singletonList(new SimpleGrantedAuthority("COMPANY"));
   }

   @Override
   public String getPassword() {
      return company.getPassword();
   }

   @Override
   public String getUsername() {
      return company.getEmail();
   }
}
