package fit.se.backend.services;

import fit.se.backend.dtos.JobDto;
import fit.se.backend.mappers.JobMapper;
import fit.se.backend.repositories.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author: vie
 * @date: 5/11/24
 */
@Service
public class JobService {
   private final JobRepository jobRepository;
   private final JobMapper jobMapper;

   public JobService(JobRepository jobRepository, JobMapper jobMapper) {
      this.jobRepository = jobRepository;
      this.jobMapper = jobMapper;
   }

   public Page<JobDto> findAllWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return jobRepository.findAll(pageable).map(jobMapper::toDto);
   }

   public Page<JobDto> search(String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return jobRepository.findJobRelativeByJobNameOrJobDescOrSkillName("%" + keyword + "%", pageable)
                   .map(jobMapper::toDto);
   }
}
