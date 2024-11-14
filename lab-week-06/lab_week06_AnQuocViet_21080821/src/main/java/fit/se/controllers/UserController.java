package fit.se.controllers;

import fit.se.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@Controller
@RequestMapping("/users")
public class UserController {
   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/{id}")
   public ModelAndView findById(@PathVariable Long id, HttpSession session) {
      ModelAndView mav = new ModelAndView("users/user-detail");
      mav.addObject("user", userService.findById(id));
      return mav;
   }
}
