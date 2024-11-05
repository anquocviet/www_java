package fit.se.backend.repositories;

import fit.se.backend.ids.JobSkillId;
import fit.se.backend.models.JobSkill;
import org.springframework.data.repository.CrudRepository;

public interface JobSkillRepository extends CrudRepository<JobSkill, JobSkillId> {
}