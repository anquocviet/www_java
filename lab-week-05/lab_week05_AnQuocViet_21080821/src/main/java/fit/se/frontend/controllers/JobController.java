package fit.se.frontend.controllers;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.JobDto;
import fit.se.backend.services.CandidateService;
import fit.se.backend.services.JobService;
import fit.se.backend.utils.JobManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
   private final JobManager jobManager;

   public JobController(JobService jobService, CandidateService candidateService, JobManager jobManager) {
      this.jobService = jobService;
      this.candidateService = candidateService;
      this.jobManager = jobManager;
   }

   @GetMapping(value = {"", "/"})
   public ModelAndView listJobs(Optional<Integer> page, Optional<Integer> size, Optional<String> search) {
      ModelAndView mav = new ModelAndView("jobs/jobs-paging");
      int currentPage = page.orElse(1);
      int pageSize = size.orElse(10);
      // Check if search is present
      Page<JobDto> jobPage;
      if (search.isPresent() && !search.get().isEmpty()) {
         jobPage = jobService.search(search.get(), currentPage - 1, pageSize, "id", "asc");
         mav.addObject("search", search.get());
      } else {
         jobPage = jobService.findAllWithPagination(
               currentPage - 1, pageSize, "id", "asc");
      }

      mav.addObject("jobPage", jobPage);
      int totalPages = jobPage.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                           .boxed()
                                           .toList();
         mav.addObject("pageNumbers", pageNumbers);
      }
      return mav;
   }

   @GetMapping("/candidate/{id}")
   public ModelAndView listJobsForCandidate(@PathVariable Long id) {
      ModelAndView mav = new ModelAndView("jobs/jobs-for-candidate");
      List<JobDto> listJob = jobService.findJobsForCandidate(id);
      mav.addObject("candidate", candidateService.findById(id));
      mav.addObject("listJob", listJob);
      return mav;
   }

   @GetMapping("/{id}/candidates")
   public ModelAndView listCandidatesForJob(@PathVariable("id") Long jobId) {
      ModelAndView mav = new ModelAndView("jobs/candidates-for-job");
      JobDto job = jobService.findById(jobId);
      mav.addObject("job", job);
      mav.addObject("listCandidate", candidateService.findCandidatesForJob(jobId));
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
   public ModelAndView add() {
      ModelAndView mav = new ModelAndView("jobs/add");
      return mav;
   }
}
