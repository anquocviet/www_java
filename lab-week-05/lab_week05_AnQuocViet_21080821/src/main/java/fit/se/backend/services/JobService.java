package fit.se.backend.services;

import fit.se.backend.dtos.CreateJobDto;
import fit.se.backend.dtos.CreateJobSkillDto;
import fit.se.backend.dtos.JobDto;
import fit.se.backend.exceptions.AppException;
import fit.se.backend.ids.JobSkillId;
import fit.se.backend.mappers.JobMapper;
import fit.se.backend.models.Candidate;
import fit.se.backend.models.Job;
import fit.se.backend.models.JobSkill;
import fit.se.backend.repositories.CandidateRepository;
import fit.se.backend.repositories.CompanyRepository;
import fit.se.backend.repositories.JobRepository;
import fit.se.backend.repositories.JobSkillRepository;
import fit.se.backend.repositories.SkillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description
 * @author: vie
 * @date: 5/11/24
 */
@Service
public class JobService {
   private final JobRepository jobRepository;
   private final CandidateRepository candidateRepository;
   private final CompanyRepository companyRepository;
   private final SkillRepository skillRepository;
   private final JobSkillRepository jobSkillRepository;
   private final JobMapper jobMapper;

   public JobService(
         JobRepository jobRepository,
         JobMapper jobMapper,
         CandidateRepository candidateRepository,
         CompanyRepository companyRepository,
         SkillRepository skillRepository, JobSkillRepository jobSkillRepository
   ) {
      this.jobRepository = jobRepository;
      this.jobMapper = jobMapper;
      this.candidateRepository = candidateRepository;
      this.companyRepository = companyRepository;
      this.skillRepository = skillRepository;
      this.jobSkillRepository = jobSkillRepository;
   }

   public Page<JobDto> findAllWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return jobRepository.findAll(pageable).map(jobMapper::toDto);
   }

   public JobDto findById(Long id) {
      return jobRepository.findById(id)
                   .map(jobMapper::toDto)
                   .orElseThrow(() -> new AppException(404, "Job not found"));
   }

   public Page<JobDto> search(String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return jobRepository.searchJobsRelativeByJobNameOrJobDescOrSkillName("%" + keyword + "%", pageable)
                   .map(jobMapper::toDto);
   }

   /**
    * Finds jobs that match the skills of a given candidate.
    * Only jobs that have at least half of the candidate's skills are returned.
    *
    * @param candidateId the ID of the candidate
    * @return a list of JobDto objects that match the candidate's skills
    * @throws AppException if the candidate is not found
    */
   public List<JobDto> findJobsForCandidate(Long candidateId) {
      Optional<Candidate> candidate = candidateRepository.findById(candidateId);
      if (candidate.isEmpty()) {
         throw new AppException(404, "Candidate not found");
      }
      // Find jobs that match the candidate's skills
      Stream<Job> jobStream =
            candidate.get()
                  .getCandidateSkills().stream()
                  .map(
                        candidateSkill -> jobRepository.findJobsBySkillLevelAndSkill(
                              candidateSkill.getSkillLevel(), candidateSkill.getSkill().getId()
                        )
                  )
                  .flatMap(List::stream);
      // Filter jobs that have at least half of the candidate's skills
      return jobStream.filter(job -> {
               long matchingSkills = job.getJobSkills().stream().filter(
                     jobSkill -> candidate.get().getCandidateSkills().stream().anyMatch(
                           candidateSkill ->
                                 candidateSkill.getSkill().getId().equals(jobSkill.getSkill().getId()) &&
                                       candidateSkill.getSkillLevel().equals(jobSkill.getSkillLevel())
                     )
               ).count();
               return matchingSkills >= job.getJobSkills().size() / 2.0;
            })
                   .map(jobMapper::toDto)
                   .toList();
   }

   public List<JobDto> findJobsOfCompany(Long companyId) {
      return jobRepository.findJobsByCompanyId(companyId)
                   .stream()
                   .map(jobMapper::toDto)
                   .toList();
   }

   public JobDto save(CreateJobDto jobDto) {
      Job job = new Job();
      job.setJobName(jobDto.jobName());
      job.setJobDesc(jobDto.jobDesc());
      companyRepository
            .findById(jobDto.company())
            .ifPresentOrElse(
                  job::setCompany,
                  () -> {
                     throw new AppException(404, "Company not found");
                  }
            );
      List<CreateJobSkillDto> createJobSkillDtos = jobDto.jobSkills();
      Set<JobSkill> listJobSkill =
            createJobSkillDtos.stream()
                  .map(cJobSkill -> {
                     JobSkill jobSkill = getJobSkill(cJobSkill, job);
                     skillRepository
                           .findById(cJobSkill.getSkillId())
                           .ifPresentOrElse(
                                 jobSkill::setSkill,
                                 () -> {
                                    throw new AppException(404, "Skill not found");
                                 }
                           );
                     return jobSkill;
                  })
                  .collect(Collectors.toSet());
      job.setJobSkills(listJobSkill);

      jobRepository.save(job);
      jobSkillRepository.saveAll(listJobSkill);
      return jobMapper.toDto(job);
   }

   private static JobSkill getJobSkill(CreateJobSkillDto cJobSkill, Job job) {
      JobSkill jobSkill = new JobSkill();

      JobSkillId jobSkillId = new JobSkillId(); // JobSkillId is a composite key, so we need to create an instance of it
      jobSkillId.setJobId(job.getId());
      jobSkillId.setSkillId(cJobSkill.getSkillId());
      jobSkill.setId(jobSkillId);

      jobSkill.setJob(job); // In turn, we need to set the job to the jobSkill
      jobSkill.setSkillLevel(cJobSkill.getSkillLevel());
      jobSkill.setMoreInfos(cJobSkill.getMoreInfos());
      return jobSkill;
   }
}
