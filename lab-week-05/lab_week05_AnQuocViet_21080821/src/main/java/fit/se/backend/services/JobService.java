package fit.se.backend.services;

import fit.se.backend.dtos.JobDto;
import fit.se.backend.exceptions.AppException;
import fit.se.backend.mappers.JobMapper;
import fit.se.backend.models.Candidate;
import fit.se.backend.repositories.CandidateRepository;
import fit.se.backend.repositories.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @description
 * @author: vie
 * @date: 5/11/24
 */
@Service
public class JobService {
   private final JobRepository jobRepository;
   private final JobMapper jobMapper;
   private final CandidateRepository candidateRepository;

   public JobService(JobRepository jobRepository, JobMapper jobMapper, CandidateRepository candidateRepository) {
      this.jobRepository = jobRepository;
      this.jobMapper = jobMapper;
      this.candidateRepository = candidateRepository;
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

   public List<JobDto> findJobsForCandidate(Long candidateId) {
      Optional<Candidate> candidate = candidateRepository.findById(candidateId);
      if (candidate.isEmpty()) {
         throw new AppException(404, "Candidate not found");
      }
      return candidate.get()
                   .getCandidateSkills().stream()
                   .map(
                         candidateSkill ->
                               jobRepository.findJobsBySkillLevelAndSkillName(
                                     candidateSkill.getSkillLevel(), candidateSkill.getSkill().getSkillName()
                               )
                   )
                   .flatMap(List::stream)
                   .map(jobMapper::toDto)
                   .toList();

   }

   public List<JobDto> findJobsOfCompany(Long companyId) {
      return jobRepository.findJobsByCompanyId(companyId)
                   .stream()
                   .map(jobMapper::toDto)
                   .toList();
   }
}
