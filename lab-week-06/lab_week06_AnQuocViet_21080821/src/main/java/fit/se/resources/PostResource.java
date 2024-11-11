package fit.se.resources;

import fit.se.dtos.PostDto;
import fit.se.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
   public ResponseEntity<List<PostDto>> findAll() {
      return ResponseEntity.ok(postService.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<PostDto> findById(@PathVariable Long id) {
      return ResponseEntity.ok(postService.findById(id));
   }

   @PostMapping("/add")
   public ResponseEntity<PostDto> save(PostDto postDto) {
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
