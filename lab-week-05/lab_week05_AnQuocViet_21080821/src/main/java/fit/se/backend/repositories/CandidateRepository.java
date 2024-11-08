package fit.se.backend.repositories;

import fit.se.backend.enums.SkillLevel;
import fit.se.backend.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CandidateRepository extends CrudRepository<Candidate, Long>, PagingAndSortingRepository<Candidate, Long> {
   @Query("""
         select c from Candidate c where
         upper(c.fullName) like upper(?1) or
         upper(c.phone) like upper(?1) or
         upper(c.email) like upper(?1)
         """)
   Page<Candidate> searchCandidatesRelativeByFullNameOrPhoneOrEmail(String keyword, Pageable pageable);

   @Query("""
         select c from Candidate c inner join c.candidateSkills candidateSkills
         where candidateSkills.skillLevel = ?1 and candidateSkills.skill.skillName = ?2
         """)
   List<Candidate> findCandidatesBySkillLevelAndSkillName(SkillLevel skillLevel, String skillName);
}