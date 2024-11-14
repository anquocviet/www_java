package fit.se.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Post}
 */
public record CreatePostDto(
      @NotNull @Size(max = 75) String title,
      Long parentPost,
      @Size(max = 100) String metaTitle,
      String summary,
      String content
) implements Serializable {
}