package fit.se.backend.repositories;

import fit.se.backend.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Long>, PagingAndSortingRepository<Candidate, Long> {
   @Query("select c from Candidate c where" +
                " upper(c.fullName) like upper(?1) or" +
                " upper(c.phone) like upper(?1) or" +
                " upper(c.email) like upper(?1)")
   Page<Candidate> searchCandidatesRelativeByFullNameOrPhoneOrEmail(String keyword, Pageable pageable);
}