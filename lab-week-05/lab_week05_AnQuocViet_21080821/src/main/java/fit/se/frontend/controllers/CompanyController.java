package fit.se.frontend.controllers;

import fit.se.backend.dtos.CompanyDto;
import fit.se.backend.dtos.JobDto;
import fit.se.backend.services.CompanyService;
import fit.se.backend.services.JobService;
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
 * @date: 6/11/24
 */
@Controller
@RequestMapping("/companies")
public class CompanyController {
   private final CompanyService companyService;
   private final JobService jobService;

   public CompanyController(CompanyService companyService, JobService jobService) {
      this.companyService = companyService;
      this.jobService = jobService;
   }

   @GetMapping(value = {"", "/"})
   public ModelAndView findAllWithPagination(Optional<Integer> page, Optional<Integer> size, Optional<String> search) {
      ModelAndView mav = new ModelAndView("companies/companies-paging");
      int currentPage = page.orElse(1);
      int pageSize = size.orElse(10);
      // Check if search is present
      Page<CompanyDto> companyPage;
      if (search.isPresent() && !search.get().isEmpty()) {
         companyPage = companyService.search(
               search.get(), currentPage - 1, pageSize, "id", "asc");
         mav.addObject("search", search.get());
      } else {
         companyPage = companyService.findAllWithPagination(
               currentPage - 1, pageSize, "id", "asc");
      }
      mav.addObject("companyPage", companyPage);

      int totalPages = companyPage.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                           .boxed()
                                           .toList();
         mav.addObject("pageNumbers", pageNumbers);
      }
      return mav;
   }

   @GetMapping("/{id}")
   public ModelAndView findById(@PathVariable Long id) {
      ModelAndView mav = new ModelAndView("companies/company-details");
      CompanyDto companyDto = companyService.findById(id);
      List<JobDto> jobsOfCompany = jobService.findJobsOfCompany(id);
      mav.addObject("company", companyDto);
      mav.addObject("listJob", jobsOfCompany);
      return mav;
   }
}
