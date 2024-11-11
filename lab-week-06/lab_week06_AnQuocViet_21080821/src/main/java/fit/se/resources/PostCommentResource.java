package fit.se.resources;

import fit.se.dtos.PostCommentDto;
import fit.se.services.PostCommentService;
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
@RequestMapping("/post-comments")
public class PostCommentResource {
   private final PostCommentService postCommentService;

   public PostCommentResource(PostCommentService postCommentService) {
      this.postCommentService = postCommentService;
   }

   @GetMapping(value = {"", "/"})
   public ResponseEntity<List<PostCommentDto>> findAll() {
      return ResponseEntity.ok(postCommentService.findAll());
   }

   @GetMapping(value = "/{id}")
   public ResponseEntity<PostCommentDto> findById(@PathVariable Long id) {
      return ResponseEntity.ok(postCommentService.findById(id));
   }

   @PostMapping("/add")
   public ResponseEntity<PostCommentDto> save(PostCommentDto postCommentDto) {
      return ResponseEntity.ok(postCommentService.save(postCommentDto));
   }

   @PutMapping("/update")
   public ResponseEntity<PostCommentDto> update(PostCommentDto postCommentDto) {
      return ResponseEntity.ok(postCommentService.update(postCommentDto));
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Boolean> delete(@PathVariable Long id) {
      return ResponseEntity.ok(postCommentService.delete(id));
   }
}
