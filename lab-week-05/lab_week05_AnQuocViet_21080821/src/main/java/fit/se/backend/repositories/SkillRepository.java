package fit.se.backend.repositories;

import fit.se.backend.models.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface SkillRepository extends CrudRepository<Skill, Long> {
   @Query("""
         select s, count (s.id) as total
         from JobSkill jk join Skill s on jk.skill.id = s.id
         group by s order by total desc
         limit 10
         """)
   List<Object[]> recommendSkill();
}