package fit.se.controllers;

import fit.se.dtos.UserDto;
import fit.se.services.PostService;
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
   private final PostService postService;

   public UserController(UserService userService, PostService postService) {
      this.userService = userService;
      this.postService = postService;
   }

   @GetMapping("/{id}")
   public ModelAndView findById(@PathVariable Long id, HttpSession session) {
      ModelAndView mav = new ModelAndView("users/user-detail");
      mav.addObject("user", userService.findById(id));
      if (session.getAttribute("user") != null) {
         UserDto user = (UserDto) session.getAttribute("user");
         if (user.id().equals(id)) {
            mav.addObject("posts", postService.findPostsByUserId(id, true));
         } else {
            mav.addObject("posts", postService.findPostsByUserId(id, false));
         }
      }
      return mav;
   }
}
