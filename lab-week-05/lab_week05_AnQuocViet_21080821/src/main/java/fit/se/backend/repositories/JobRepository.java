package fit.se.backend.repositories;

import fit.se.backend.dtos.SkillWithLevelDTO;
import fit.se.backend.enums.SkillLevel;
import fit.se.backend.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long>, PagingAndSortingRepository<Job, Long> {

   @Query("select j from Job j inner join j.jobSkills jobSkills where " +
         "upper(j.jobName) like upper(?1) " +
         "or upper(j.jobDesc) like upper(?1)" +
         "or upper(jobSkills.skill.skillName) like upper(?1)")
   Page<Job> searchJobsRelativeByJobNameOrJobDescOrSkillName(String keyword, Pageable pageable);

   @Query("""
         select j from Job j inner join j.jobSkills jobSkills
         where jobSkills.skillLevel = ?1 and jobSkills.skill.id = ?2""")
   List<Job> findJobsBySkillLevelAndSkill(SkillLevel skillLevel, Long skillId);

   List<Job> findJobsByCompanyId(Long companyId);

   List<Job> findJobsByIdIn(Collection<Long> id);

   @Query("""
         select new fit.se.backend.dtos.SkillWithLevelDTO(js.skill.id, js.skillLevel)
         from JobSkill js
         where js.job.id = ?1
         """)
   List<SkillWithLevelDTO> findSkillOfJob(Long jobId);
}