package fit.se.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import fit.se.backend.dtos.CreateCandidateDTO;
import fit.se.backend.dtos.CreateCompanyDTO;
import fit.se.backend.services.CandidateService;
import fit.se.backend.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
   private final CompanyService companyService;
   private final CandidateService candidateService;

   public AuthController(CompanyService companyService, CandidateService candidateService) {
      this.companyService = companyService;
      this.candidateService = candidateService;
   }

   @GetMapping("/login")
   public String login(Optional<String> error, Model model) {
      error.ifPresent(e -> model.addAttribute("error", "Đăng nhập thất bại"));
      return "auth/login";
   }

   @GetMapping("/register")
   public String register(Model model) {
      model.addAttribute("countries", CountryCode.values());
      return "auth/register";
   }

   @GetMapping("/register/candidate/experience")
   public String registerCandidateExperience() {
      return "auth/register-candidate-experience";
   }


   @PostMapping("/register/company")
   public String registerCompany(@Valid CreateCompanyDTO companyDTO) {
      companyService.save(companyDTO);
      return "redirect:/auth/login";
   }

   @PostMapping("/register/candidate")
   public String registerCandidate(@Valid CreateCandidateDTO candidateDTO) {
      candidateService.save(candidateDTO);
      return "redirect:/auth/login";
   }
}
