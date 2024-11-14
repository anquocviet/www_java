package fit.se.resources;

import fit.se.dtos.CreatePostDto;
import fit.se.dtos.PostDto;
import fit.se.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@RestController
@RequestMapping("/posts")
public class PostResource {
   private final PostService postService;

   public PostResource(PostService postService) {
      this.postService = postService;
   }

   @GetMapping(value = {"", "/"})
   public ResponseEntity<Page<PostDto>> findAll(Optional<Integer> page, Optional<Integer> size) {
      int currentPage = page.orElse(1);
      int pageSize = size.orElse(10);
      Page<PostDto> postPage = postService.findAll(currentPage - 1, pageSize, "publishedAt", "asc");
      return ResponseEntity.ok(postPage);
   }

   @GetMapping("/{id}")
   public ResponseEntity<PostDto> findById(@PathVariable Long id) {
      return ResponseEntity.ok(postService.findById(id));
   }

   @PostMapping("/add")
   public ResponseEntity<PostDto> save(CreatePostDto postDto) {
      return ResponseEntity.ok(postService.save(postDto));
   }

   @PutMapping("/update")
   public ResponseEntity<PostDto> update(PostDto postDto) {
      return ResponseEntity.ok(postService.update(postDto));
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Boolean> delete(@PathVariable Long id) {
      return ResponseEntity.ok(postService.delete(id));
   }
}
