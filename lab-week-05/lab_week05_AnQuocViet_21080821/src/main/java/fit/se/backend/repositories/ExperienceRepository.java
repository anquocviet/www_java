package fit.se.backend.repositories;

import fit.se.backend.models.Experience;
import org.springframework.data.repository.CrudRepository;

public interface ExperienceRepository extends CrudRepository<Experience, Long> {
}