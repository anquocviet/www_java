package fit.se.frontend.controllers;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.CompanyDto;
import fit.se.backend.dtos.CreateJobDto;
import fit.se.backend.dtos.CreateJobSkillDto;
import fit.se.backend.dtos.JobDto;
import fit.se.backend.enums.SkillLevel;
import fit.se.backend.exceptions.impl.ForBidenException;
import fit.se.backend.security.CandidateDetails;
import fit.se.backend.security.CompanyDetails;
import fit.se.backend.services.CandidateRecommendationService;
import fit.se.backend.services.CandidateService;
import fit.se.backend.services.CompanyService;
import fit.se.backend.services.JobRecommendationService;
import fit.se.backend.services.JobService;
import fit.se.backend.services.SkillService;
import fit.se.backend.utils.JobManager;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @description
 * @author: vie
 * @date: 5/11/24
 */
@Controller
@RequestMapping("/jobs")
public class JobController {
   private final JobService jobService;
   private final CandidateService candidateService;
   private final CompanyService companyService;
   private final JobManager jobManager;
   private final SkillService skillService;
   private final JobRecommendationService jobRecommendationService;
   private final CandidateRecommendationService candidateRecommendationService;

   public JobController(
         JobService jobService,
         CandidateService candidateService,
         CompanyService companyService,
         JobManager jobManager, SkillService skillService,
         JobRecommendationService jobRecommendationService, CandidateRecommendationService candidateRecommendationService) {
      this.jobService = jobService;
      this.candidateService = candidateService;
      this.companyService = companyService;
      this.jobManager = jobManager;
      this.skillService = skillService;
      this.jobRecommendationService = jobRecommendationService;
      this.candidateRecommendationService = candidateRecommendationService;
   }

   @GetMapping(value = {"", "/"})
   public ModelAndView listJobs(Optional<Integer> page, Optional<Integer> size, Optional<String> search, HttpSession session) {
      ModelAndView mav = new ModelAndView("jobs/jobs-paging");
      int currentPage = page.orElse(1);
      int pageSize = size.orElse(10);
      // Check if search is present
      Page<JobDto> jobPage;
      if (search.isPresent() && !search.get().isEmpty()) {
         jobPage = jobService.search(search.get(), currentPage - 1, pageSize, "id", "desc");
         mav.addObject("search", search.get());
      } else {
         jobPage = jobService.findAllWithPagination(
               currentPage - 1, pageSize, "id", "desc");
      }

      mav.addObject("jobPage", jobPage);
      int totalPages = jobPage.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
               .boxed()
               .toList();
         mav.addObject("pageNumbers", pageNumbers);
      }

//      Check role and config for the view
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COMPANY"))) {
         String emailCom = ((CompanyDetails) auth.getPrincipal()).getUsername();
         session.setAttribute("company", companyService.findByEmail(emailCom));
      } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CANDIDATE"))) {
         String emailCan = ((CandidateDetails) auth.getPrincipal()).getUsername();
         session.setAttribute("candidate", candidateService.findByEmail(emailCan));
      }

      return mav;
   }

   @GetMapping("/recommend")
   public ModelAndView listJobsForCandidate(HttpSession session) {
      Object candidateObj = session.getAttribute("candidate");
      if (candidateObj == null) {
         throw new ForBidenException();
      }
      CandidateDto candidate = (CandidateDto) candidateObj;
      ModelAndView mav = new ModelAndView("jobs/jobs-for-candidate");
      List<JobDto> listJob = jobRecommendationService.recommendJobs(candidate.id());
      mav.addObject("candidate", candidateService.findById(candidate.id()));
      mav.addObject("candidateSkills", candidate.skills());
      mav.addObject("listJob", listJob);
      return mav;
   }

   @GetMapping("/{id}/candidates")
   public ModelAndView listCandidatesForJob(@PathVariable("id") Long jobId) {
      ModelAndView mav = new ModelAndView("jobs/candidates-for-job");
      JobDto job = jobService.findById(jobId);
      mav.addObject("job", job);
      mav.addObject("listCandidate", candidateRecommendationService.recommendCandidates(jobId));
      mav.addObject("jobSkills", job.jobSkills());
      return mav;
   }

   @GetMapping("/{jobId}/{candidateId}/send-email")
   public ModelAndView sendEmail(@PathVariable("jobId") Long jobId, @PathVariable("candidateId") Long candidateId) {
      ModelAndView mav = new ModelAndView("jobs/send-email");
      JobDto job = jobService.findById(jobId);
      CandidateDto candidate = candidateService.findById(candidateId);
      jobManager.sendEmail(job, candidate);

      mav.addObject("job", job);
      mav.addObject("candidate", candidate);
      return mav;
   }

   @GetMapping("/add")
   public ModelAndView add(HttpSession session) {
      ModelAndView mav = new ModelAndView("jobs/add");
      Object companyObj = session.getAttribute("company");
      if (companyObj == null) {
         throw new ForBidenException();
      }
      CompanyDto company = (CompanyDto) companyObj;
      mav.addObject("skills", skillService.findAll());
      mav.addObject("skillLevels", SkillLevel.values());
      mav.addObject("job",
            new CreateJobDto("", "", company.id(),
                  List.of(new CreateJobSkillDto(1L, SkillLevel.MASTER, ""))
            )
      );
      return mav;
   }

   @PostMapping("/add")
   public ModelAndView add(@Valid @ModelAttribute CreateJobDto job) {
      jobService.save(job);
      return new ModelAndView("redirect:/jobs");
   }

}
