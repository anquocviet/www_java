package fit.se.backend.services;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.JobDto;
import fit.se.backend.exceptions.AppException;
import fit.se.backend.mappers.CandidateMapper;
import fit.se.backend.models.Candidate;
import fit.se.backend.repositories.CandidateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 4/11/24
 */
@Service
public class CandidateService {
   private final CandidateRepository candidateRepository;
   private final AddressService addressService;
   private final JobService jobService;
   private final CandidateMapper candidateMapper;
   private final Logger logger = Logger.getLogger(CandidateService.class.getName());

   public CandidateService(
         CandidateRepository candidateRepository,
         AddressService addressService,
         JobService jobService,
         CandidateMapper candidateMapper
   ) {
      this.candidateRepository = candidateRepository;
      this.addressService = addressService;
      this.jobService = jobService;
      this.candidateMapper = candidateMapper;
   }

   public Page<CandidateDto> findAllWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return candidateRepository.findAll(pageable).map(candidateMapper::toDto);
   }

   public CandidateDto findById(Long id) {
      return candidateRepository.findById(id)
                   .map(candidateMapper::toDto)
                   .orElseThrow(() -> {
                      logger.log(Level.SEVERE, "Candidate not found");
                      return new AppException(404, "Candidate not found");
                   });
   }

   public CandidateDto update(CandidateDto candidateDto) {
      boolean isCanExists = candidateRepository.existsById(candidateDto.id());
      if (!isCanExists) {
         throw new AppException(404, "Candidate not found");
      }
      addressService.update(candidateDto.address());
      Candidate candidate = candidateMapper.toEntity(candidateDto);
      candidateRepository.save(candidate);
      return candidateMapper.toDto(candidate);
   }

   public Page<CandidateDto> search(String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return candidateRepository
                   .searchCandidatesRelativeByFullNameOrPhoneOrEmail("%" + keyword + "%", pageable)
                   .map(candidateMapper::toDto);
   }

   public List<CandidateDto> findCandidatesForJob(Long jobId) {
      JobDto job = jobService.findById(jobId);
      return job.jobSkills().stream()
                   .map(jobSkill -> candidateRepository.findCandidatesBySkillLevelAndSkillName(
                         jobSkill.skillLevel(), jobSkill.skill().skillName()))
                   .flatMap(List::stream)
                   .map(candidateMapper::toDto)
                   .toList();
   }
}
