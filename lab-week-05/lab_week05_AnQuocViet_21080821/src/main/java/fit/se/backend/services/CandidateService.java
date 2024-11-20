package fit.se.backend.services;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.CandidateSkillDto;
import fit.se.backend.dtos.CreateCandidateDTO;
import fit.se.backend.dtos.JobDto;
import fit.se.backend.exceptions.AppException;
import fit.se.backend.exceptions.impl.CandidateNotFound;
import fit.se.backend.exceptions.impl.EmailAlreadyExistsException;
import fit.se.backend.exceptions.impl.PhoneAlreadyExistsException;
import fit.se.backend.mappers.CandidateMapper;
import fit.se.backend.mappers.CandidateSkillMapper;
import fit.se.backend.models.Candidate;
import fit.se.backend.repositories.AddressRepository;
import fit.se.backend.repositories.CandidateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

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
   private final CandidateSkillMapper cSkillMapper;
   private final Logger logger = Logger.getLogger(CandidateService.class.getName());
   private final PasswordEncoder passwordEncoder;
   private final AddressRepository addressRepository;

   public CandidateService(
         CandidateRepository candidateRepository,
         AddressService addressService,
         JobService jobService,
         CandidateMapper candidateMapper,
         CandidateSkillMapper cSkillMapper,
         PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
      this.candidateRepository = candidateRepository;
      this.addressService = addressService;
      this.jobService = jobService;
      this.candidateMapper = candidateMapper;
      this.cSkillMapper = cSkillMapper;
      this.passwordEncoder = passwordEncoder;
      this.addressRepository = addressRepository;
   }

   public Page<CandidateDto> findAllWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return candidateRepository.findAll(pageable).map(candidateMapper::toDto);
   }

   public CandidateDto findById(Long id) {
      return candidateRepository.findById(id)
            .map(candidateMapper::toDto)
            .orElseThrow(CandidateNotFound::new);
   }

   public CandidateDto save(CreateCandidateDTO candidateDTO) {
      if (candidateRepository.existsCandidateByEmail(candidateDTO.email())) {
         throw new EmailAlreadyExistsException();
      }
      if (candidateRepository.existsCandidateByPhone(candidateDTO.phone())) {
         throw new PhoneAlreadyExistsException();
      }
      Candidate candidate = candidateMapper.toEntity(candidateDTO);
      String hashedPassword = passwordEncoder.encode(candidateDTO.password());
      candidate.setPassword(hashedPassword);
      Candidate canSave = candidateRepository.save(candidate);
      return candidateMapper.toDto(canSave);
   }

   public CandidateDto update(CandidateDto candidateDto) {
      boolean isCanExists = candidateRepository.existsById(candidateDto.id());
      if (!isCanExists) {
         throw new CandidateNotFound();
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

   /**
    * Finds candidates that match the skills of a given job.
    * Only candidates that have at least half of the job's skills are returned.
    *
    * @param jobId the ID of the job
    * @return a list of CandidateDto objects that match the job's skills
    * @throws AppException if the job is not found
    */
   public List<CandidateDto> findCandidatesForJob(Long jobId) {
      JobDto job = jobService.findById(jobId);
      // Find candidates that match the job's skills
      Stream<Candidate> candidateStream =
            job.jobSkills().stream()
                  .map(jobSkill -> candidateRepository.findCandidatesBySkillLevelAndSkill(
                        jobSkill.skillLevel(), jobSkill.skill().id()))
                  .flatMap(List::stream);
      // Filter candidates that have at least half of the job's skills
      return candidateStream.filter(
            candidate -> {
               long matchingSkills = candidate.getCandidateSkills().stream().filter(
                     candidateSkill -> job.jobSkills().stream().anyMatch(
                           jobSkill -> jobSkill.skill().id().equals(candidateSkill.getSkill().getId()) &&
                                 jobSkill.skillLevel().equals(candidateSkill.getSkillLevel())
                     )
               ).count();
               return matchingSkills >= job.jobSkills().size() / 2.0;
            }
      ).map(candidateMapper::toDto).toList();
   }

   public List<CandidateSkillDto> findSkillsOfCandidate(Long id) {
      return candidateRepository.findById(id)
            .map(Candidate::getCandidateSkills)
            .stream()
            .flatMap(Set::stream)
            .map(cSkillMapper::toDto)
            .toList();
   }

   public CandidateDto findByEmail(String email) {
      return candidateRepository.findCandidateByEmail(email)
            .map(candidateMapper::toDto)
            .orElseThrow(CandidateNotFound::new);
   }
}
