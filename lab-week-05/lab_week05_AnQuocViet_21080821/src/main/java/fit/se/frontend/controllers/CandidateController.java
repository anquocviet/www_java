package fit.se.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.enums.SkillLevel;
import fit.se.backend.services.CandidateService;
import fit.se.backend.services.SkillRecommendationService;
import fit.se.backend.services.SkillService;
import jakarta.servlet.http.HttpSession;
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
   private final SkillService skillService;
   private final SkillRecommendationService skillRecommendationService;

   public CandidateController(CandidateService candidateService, SkillService skillService, SkillRecommendationService skillRecommendationService) {
      this.candidateService = candidateService;
      this.skillService = skillService;
      this.skillRecommendationService = skillRecommendationService;
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

   @GetMapping("/{id}")
   public ModelAndView showCandidate(@PathVariable Long id) {
      ModelAndView mav = new ModelAndView("candidates/candidate-details");
      CandidateDto candidate = candidateService.findById(id);
      mav.addObject("candidate", candidate);
      mav.addObject("skills", candidate.skills());
      return mav;
   }

   @GetMapping("/skill-recommend")
   public ModelAndView skillRecommendation() {
      ModelAndView mav = new ModelAndView("candidates/skill-recommend");
      mav.addObject("skills", skillRecommendationService.recommendSkill());
      return mav;
   }


   @GetMapping("/edit")
   public ModelAndView editCandidate(HttpSession session) {
      CandidateDto candidate = (CandidateDto) session.getAttribute("candidate");
      ModelAndView mav = new ModelAndView("candidates/edit");
      mav.addObject("candidate", candidate);
      mav.addObject("countries", CountryCode.values());
      mav.addObject("skills", skillService.findAll());
      mav.addObject("skillLevels", SkillLevel.values());
      return mav;
   }

   @PostMapping("/update")
   public String updateCandidate(@Valid CandidateDto candidate) {
      candidateService.update(candidate);
      return "redirect:/candidates";
   }

}
