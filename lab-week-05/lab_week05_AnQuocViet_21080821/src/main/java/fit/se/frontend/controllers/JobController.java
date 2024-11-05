package fit.se.frontend.controllers;

import fit.se.backend.dtos.JobDto;
import fit.se.backend.services.JobService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

   public JobController(JobService jobService) {
      this.jobService = jobService;
   }

   @GetMapping(value = {"", "/"})
   public ModelAndView listJobs(Optional<Integer> page, Optional<Integer> size, Optional<String> search) {
      ModelAndView mav = new ModelAndView("jobs/jobs_paging");
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
}
