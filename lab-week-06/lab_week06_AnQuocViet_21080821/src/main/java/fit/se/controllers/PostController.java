package fit.se.controllers;

import fit.se.dtos.CreatePostCommentDto;
import fit.se.dtos.CreatePostDto;
import fit.se.dtos.PostDto;
import fit.se.services.PostCommentService;
import fit.se.services.PostService;
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
   public ModelAndView findAll(Optional<Integer> page, Optional<Integer> size) {
      ModelAndView mav = new ModelAndView("posts/list-post");
      int currentPage = page.orElse(1);
      int pageSize = size.orElse(10);
      Page<PostDto> postPage = postService.findAll(currentPage - 1, pageSize, "publishedAt", "desc");
      mav.addObject("posts", postPage);
      int totalPages = postPage.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                           .boxed()
                                           .toList();
         mav.addObject("pageNumbers", pageNumbers);
      }
      return mav;
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

   @PostMapping("/create")
   public ModelAndView create(@Valid CreatePostDto postDto) {
      ModelAndView mav = new ModelAndView("redirect:/posts");
      postService.save(postDto);
      return mav;
   }

   @GetMapping("/{id}/publish")
   public ModelAndView publish(@PathVariable("id") Long id) {
      ModelAndView mav = new ModelAndView("redirect:/posts");
      postService.publishPost(id);
      return mav;
   }
}
