package fit.se.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.services.CandidateService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @date: 4/11/24
 */
@Controller
@RequestMapping("/candidates")
public class CandidateController {
   private final CandidateService candidateService;

   public CandidateController(CandidateService candidateService) {
      this.candidateService = candidateService;
   }

   @GetMapping(value = {"", "/"})
   public ModelAndView listCandidates(Optional<Integer> page, Optional<Integer> size, Optional<String> search) {
      ModelAndView mav = new ModelAndView("candidates/candidates-paging");
      int currentPage = page.orElse(1);
      int pageSize = size.orElse(10);

      Page<CandidateDto> candidatePage;
      if (search.isPresent() && !search.get().isEmpty()) {
         candidatePage = candidateService.search(search.get(), currentPage - 1, pageSize, "id", "asc");
         mav.addObject("search", search.get());
      } else {
         candidatePage = candidateService.findAllWithPagination(
               currentPage - 1, pageSize, "id", "asc");
      }
      mav.addObject("candidatePage", candidatePage);

      int totalPages = candidatePage.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                           .boxed()
                                           .toList();
         mav.addObject("pageNumbers", pageNumbers);
      }
      return mav;
   }

   @GetMapping("/add")
   public ModelAndView addCandidate() {
      ModelAndView mav = new ModelAndView("candidates/add");
      mav.addObject("countries", CountryCode.values());
      return mav;
   }

   @GetMapping("/edit/{id}")
   public ModelAndView editCandidate(@PathVariable Long id) {
      ModelAndView mav = new ModelAndView("candidates/edit");
      CandidateDto candidate = candidateService.findById(id);
      mav.addObject("candidate", candidate);
      mav.addObject("countries", CountryCode.values());
      return mav;
   }

   @PostMapping("/update")
   public String updateCandidate(@Valid CandidateDto candidate) {
      candidateService.update(candidate);
      return "redirect:/candidates";
   }

}
