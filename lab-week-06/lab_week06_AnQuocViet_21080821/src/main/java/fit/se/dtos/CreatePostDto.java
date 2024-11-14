package fit.se.dtos;

import fit.se.entities.Post;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Optional;

/**
 * DTO for {@link Post}
 */
public record CreatePostDto(
      @NotNull
      Long authorId,
      @NotNull @Size(max = 75) String title,
      Optional<Long> parentPost,
      @Size(max = 100) String metaTitle,
      String summary,
      @NotEmpty
      String content,
      Boolean published
) implements Serializable {
}