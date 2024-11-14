package fit.se.controllers;

import fit.se.dtos.CreatePostCommentDto;
import fit.se.services.PostCommentService;
import fit.se.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * @description
 * @author: vie
 * @date: 13/11/24
 */
@Controller
@RequestMapping("/posts")
public class PostController {
   private final PostService postService;
   private final PostCommentService postCommentService;

   public PostController(PostService postService, PostCommentService postCommentService) {
      this.postService = postService;
      this.postCommentService = postCommentService;
   }

   @GetMapping(value = {"", "/"})
   public ModelAndView findAll() {
      ModelAndView modelAndView = new ModelAndView("posts/list-post");
      modelAndView.addObject("posts", postService.findAll());
      return modelAndView;
   }

   @GetMapping("/{id}")
   public ModelAndView findById(@PathVariable("id") Long id) {
      ModelAndView mav = new ModelAndView("posts/detail-post");
      mav.addObject("post", postService.findById(id));
      mav.addObject("comments", postCommentService.findByPostId(id));
      mav.addObject("comment", new CreatePostCommentDto("", ""));
      return mav;
   }

   @GetMapping(value = {"/create", "/parent/{parentId}/create"})
   public ModelAndView create(@PathVariable Optional<Long> parentId) {
      ModelAndView mav = new ModelAndView("posts/create-post");
      mav.addObject("parentId", parentId);
      return mav;
   }
}
