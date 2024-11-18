package fit.se.backend.security;

import fit.se.backend.models.Candidate;
import fit.se.backend.models.Company;
import fit.se.backend.repositories.CandidateRepository;
import fit.se.backend.repositories.CompanyRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
   private final CandidateRepository candidateRepository;
   private final CompanyRepository companyRepository;

   public CustomUserDetailsService(CandidateRepository candidateRepository, CompanyRepository companyRepository) {
      this.candidateRepository = candidateRepository;
      this.companyRepository = companyRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<Candidate> candidate = candidateRepository.findCandidateByEmail(username);
      if (candidate.isPresent()) {
         return new CandidateDetails(candidate.get());
      }

      Optional<Company> company = companyRepository.findCompanyByEmail(username);
      if (company.isPresent()) {
         return new CompanyDetails(company.get());
      }

      throw new UsernameNotFoundException("User not found with email: " + username);
   }
}
