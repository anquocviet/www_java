package fit.se.controllers;

import fit.se.dtos.LoginDto;
import fit.se.dtos.RegisterUserDto;
import fit.se.dtos.UserDto;
import fit.se.services.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
   private final AuthService authService;

   public AuthController(AuthService authService) {
      this.authService = authService;
   }

   @GetMapping(value = {"", "/", "/login"})
   public String login() {
      return "auth/login";
   }

   @GetMapping("/register")
   public String register() {
      return "auth/register";
   }


   @PostMapping("/login")
   public ModelAndView login(@Valid LoginDto loginDto, HttpSession session) {
      ModelAndView modelAndView = new ModelAndView("redirect:/posts");
      UserDto userDto = authService.login(loginDto);
      session.setAttribute("user", userDto);
      modelAndView.addObject("user", userDto);
      return modelAndView;
   }

   @PostMapping("/register")
   public ModelAndView register(@Valid RegisterUserDto registerUserDto) {
      ModelAndView modelAndView = new ModelAndView("redirect:/auth/login");
      boolean registerStatus = authService.register(registerUserDto);
      modelAndView.addObject("registerStatus", registerStatus);
      return modelAndView;
   }
}
