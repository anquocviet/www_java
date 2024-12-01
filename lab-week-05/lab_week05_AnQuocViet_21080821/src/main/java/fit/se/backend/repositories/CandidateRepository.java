package fit.se.backend.repositories;

import fit.se.backend.dtos.SkillWithLevelDTO;
import fit.se.backend.enums.SkillLevel;
import fit.se.backend.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

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
         where candidateSkills.skillLevel = ?1 and candidateSkills.skill.id = ?2
         """)
   List<Candidate> findCandidatesBySkillLevelAndSkill(SkillLevel skillLevel, Long skillId);

   Optional<Candidate> findCandidateByEmail(String email);

   boolean existsCandidateByEmail(String email);

   boolean existsCandidateByPhone(String phone);

   @Query("""
         select new fit.se.backend.dtos.SkillWithLevelDTO(cs.skill.id, cs.skillLevel)
         from CandidateSkill cs
         where cs.can.id = ?1
         """)
   List<SkillWithLevelDTO> findSkillOfUser(Long candidateId);

   List<Candidate> findCandidatesByIdIn(List<Long> candidateIds);
}