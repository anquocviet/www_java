package fit.se.backend.repositories;

import fit.se.backend.models.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Long>, PagingAndSortingRepository<Candidate, Long> {
}