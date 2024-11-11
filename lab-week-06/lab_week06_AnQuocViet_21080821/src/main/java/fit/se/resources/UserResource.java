package fit.se.resources;

import fit.se.dtos.UserDto;
import fit.se.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/users")
public class UserResource {
   private final UserService userService;

   public UserResource(UserService userService) {
      this.userService = userService;
   }

   @GetMapping(value = {"", "/"})
   public ResponseEntity<List<UserDto>> findAll() {
      return ResponseEntity.ok(userService.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<UserDto> findById(@PathVariable Long id) {
      return ResponseEntity.ok(userService.findById(id));
   }

   @PutMapping("/update")
   public ResponseEntity<UserDto> update(UserDto userDto) {
      return ResponseEntity.ok(userService.update(userDto));
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Boolean> delete(@PathVariable Long id) {
      return ResponseEntity.ok(userService.delete(id));
   }

}
