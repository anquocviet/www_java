package fit.se.backend.security;

import fit.se.backend.models.Candidate;
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
public class CandidateDetails implements UserDetails {
   private final Candidate candidate;

   public CandidateDetails(Candidate candidate) {
      this.candidate = candidate;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.singletonList(new SimpleGrantedAuthority("CANDIDATE"));
   }

   @Override
   public String getPassword() {
      return candidate.getPassword();
   }

   @Override
   public String getUsername() {
      return candidate.getEmail();
   }
}
