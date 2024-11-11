package fit.se.controllers;

import fit.se.services.UserService;
import org.springframework.stereotype.Controller;
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

   @RequestMapping(value = {"", "/"})
   public ModelAndView findAll() {
      ModelAndView modelAndView = new ModelAndView("users");
      modelAndView.addObject("users", userService.findAll());
      return modelAndView;
   }
}
