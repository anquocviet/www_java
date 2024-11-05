package fit.se.backend.repositories;

import fit.se.backend.ids.CandidateSkillId;
import fit.se.backend.models.CandidateSkill;
import org.springframework.data.repository.CrudRepository;

public interface CandidateSkillRepository extends CrudRepository<CandidateSkill, CandidateSkillId> {
}